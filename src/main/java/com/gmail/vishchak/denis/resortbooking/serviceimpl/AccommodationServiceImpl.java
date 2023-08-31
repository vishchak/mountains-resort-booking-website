package com.gmail.vishchak.denis.resortbooking.serviceimpl;

import com.gmail.vishchak.denis.resortbooking.exception.custom.BadRequestException;
import com.gmail.vishchak.denis.resortbooking.exception.custom.NoContentException;
import com.gmail.vishchak.denis.resortbooking.exception.custom.NotFoundException;
import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.model.search.SearchCriteria;
import com.gmail.vishchak.denis.resortbooking.repository.AccommodationRepository;
import com.gmail.vishchak.denis.resortbooking.service.AccommodationService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Override
    public Page<Accommodation> getAllAccommodations(Pageable pageable) {
        log.info("Fetching all accommodations...");
        Page<Accommodation> accommodationPage = accommodationRepository.findAll(pageable);

        if (accommodationPage.isEmpty()) {
            log.warn("No accommodations found.");
            throw new NoContentException("No accommodations found");
        }

        log.info("Retrieved {} accommodations.", accommodationPage.getNumberOfElements());
        return accommodationPage;
    }

    @Override
    public Accommodation getAccommodationById(Long id) {
        log.info("Fetching accommodation by ID: {}", id);
        return accommodationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Accommodation with ID {} not found.", id);
                    return new NotFoundException("Accommodation not found");
                });
    }

    @Override
    public Accommodation createAccommodation(Accommodation accommodation, BindingResult bindingResult) {
        log.info("Creating accommodation: {}", accommodation.getName());

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid accommodation data");
        }

        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation updateAccommodation(Long id, Accommodation updatedAccommodation, BindingResult bindingResult) {
        log.info("Updating accommodation with ID {}: {}", id, updatedAccommodation.getName());

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("Invalid accommodation data");
        }

        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Accommodation with ID {} not found.", id);
                    return new NotFoundException("Accommodation not found");
                });

        return accommodationRepository.save(accommodation);
    }

    @Override
    public void deleteAccommodation(Long id) {
        log.info("Deleting accommodation with ID: {}", id);
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Accommodation with ID {} not found.", id);
                    return new NotFoundException("Accommodation not found");
                });

        accommodationRepository.delete(accommodation);
    }

    @Override
    public Page<Accommodation> searchAccommodationsByCriteria(SearchCriteria criteria, Pageable pageable) {
        log.info("Searching accommodations by criteria: {}", criteria);

        Specification<Accommodation> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null) {
                predicates.add(builder.like(builder.lower(root.get("name")), "%" + criteria.getKeyword().toLowerCase() + "%"));
            }
            if (criteria.getMinPrice() != null) {
                predicates.add(builder.ge(root.get("price"), criteria.getMinPrice()));
            }
            if (criteria.getMaxPrice() != null) {
                predicates.add(builder.le(root.get("price"), criteria.getMaxPrice()));
            }

            if (criteria.getTags() != null && !criteria.getTags().isEmpty()) {
                predicates.add(root.join("tags").get("tagName").in(criteria.getTags()));
            }

            if (criteria.getAmenities() != null && !criteria.getAmenities().isEmpty()) {
                predicates.add(root.join("amenities").get("name").in(criteria.getAmenities()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };

        return accommodationRepository.findAll(specification, pageable);
    }
}
