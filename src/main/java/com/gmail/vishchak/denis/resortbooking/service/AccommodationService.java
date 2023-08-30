package com.gmail.vishchak.denis.resortbooking.service;

import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.model.search.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {

    Page<Accommodation> getAllAccommodations(Pageable pageable);

    Accommodation getAccommodationById(Long id);

    Accommodation createAccommodation(Accommodation accommodation);

    Accommodation updateAccommodation(Long id, Accommodation updatedAccommodation);

    void deleteAccommodation(Long id);

    Page<Accommodation> searchAccommodationsByCriteria(SearchCriteria criteria, Pageable pageable);
}
