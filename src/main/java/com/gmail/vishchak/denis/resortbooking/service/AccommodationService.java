package com.gmail.vishchak.denis.resortbooking.service;

import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.model.search.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface AccommodationService {

    Page<Accommodation> getAllAccommodations(Pageable pageable);

    Accommodation getAccommodationById(Long id);

    Accommodation createAccommodation(Accommodation accommodation, BindingResult bindingResult);

    Accommodation updateAccommodation(Long id, Accommodation updatedAccommodation, BindingResult bindingResult);

    void deleteAccommodation(Long id);

    Page<Accommodation> searchAccommodationsByCriteria(SearchCriteria criteria, Pageable pageable);
}
