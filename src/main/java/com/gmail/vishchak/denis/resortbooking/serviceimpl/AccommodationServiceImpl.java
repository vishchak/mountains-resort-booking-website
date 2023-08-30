package com.gmail.vishchak.denis.resortbooking.serviceimpl;

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
        Page<Accommodation> accommodationPage = accommodationRepository.findAll(pageable);

        if (accommodationPage.isEmpty()) throw new NoContentException("No accommodations found");

        return accommodationPage;
    }

    @Override
    public Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Accommodation not found"));
    }

    @Override
    public Accommodation createAccommodation(Accommodation accommodation) {
        return null;
    }

    @Override
    public Accommodation updateAccommodation(Long id, Accommodation updatedAccommodation) {
        return null;
    }

    @Override
    public void deleteAccommodation(Long id) {

    }

    @Override
    public Page<Accommodation> searchAccommodationsByCriteria(SearchCriteria criteria, Pageable pageable) {
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
