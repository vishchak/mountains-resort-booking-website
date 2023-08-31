package com.gmail.vishchak.denis.resortbooking.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gmail.vishchak.denis.resortbooking.exception.custom.NoContentException;
import com.gmail.vishchak.denis.resortbooking.model.Amenity;
import com.gmail.vishchak.denis.resortbooking.model.Tag;
import com.gmail.vishchak.denis.resortbooking.service.AccommodationService;
import com.gmail.vishchak.denis.resortbooking.serviceimpl.AccommodationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.model.search.SearchCriteria;
import com.gmail.vishchak.denis.resortbooking.repository.AccommodationRepository;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccommodationServiceTest {
    @Test
    public void testCreateAccommodation() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        Accommodation mockAccommodation = Accommodation.builder()
                .name("Sample Accommodation")
                .description("This is a sample accommodation for testing purposes.")
                .price(100.0)
                .build();
        BindingResult bindingResult = mock(BindingResult.class);

        when(repository.save(any())).thenReturn(mockAccommodation);

        Accommodation result = service.createAccommodation(mockAccommodation, bindingResult);

        assertNotNull(result);
        assertEquals("Sample Accommodation", result.getName());
    }

    @Test
    public void testUpdateAccommodation() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        Long accommodationId = 1L;
        Accommodation mockAccommodation = Accommodation.builder()
                .id(accommodationId)
                .name("Sample Accommodation")
                .description("This is a sample accommodation for testing purposes.")
                .price(100.0)
                .build();
        BindingResult bindingResult = mock(BindingResult.class);

        when(repository.findById(accommodationId)).thenReturn(Optional.of(mockAccommodation));
        when(repository.save(any())).thenReturn(mockAccommodation);

        Accommodation updatedAccommodation = service.updateAccommodation(accommodationId, mockAccommodation, bindingResult);

        assertNotNull(updatedAccommodation);
        assertEquals("Sample Accommodation", updatedAccommodation.getName());
    }

    @Test
    public void testDeleteAccommodation() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        Long accommodationId = 1L;
        Accommodation mockAccommodation = Accommodation.builder()
                .id(accommodationId)
                .name("Sample Accommodation")
                .description("This is a sample accommodation for testing purposes.")
                .price(100.0)
                .build();
        when(repository.findById(accommodationId)).thenReturn(Optional.of(mockAccommodation));

        assertDoesNotThrow(() -> service.deleteAccommodation(accommodationId));
    }
    @Test
    public void testGetAllAccommodations() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        Page<Accommodation> mockPage = new PageImpl<>(Collections.emptyList());
        Pageable pageable = Pageable.unpaged();
        when(repository.findAll(pageable)).thenReturn(mockPage);

        assertThrows(NoContentException.class, () -> service.getAllAccommodations(pageable));
    }

    @Test
    public void testGetAccommodationById() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        Accommodation mockAccommodation = Accommodation.builder()
                .name("Sample Accommodation")
                .description("This is a sample accommodation for testing purposes.")
                .price(100.0)
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(mockAccommodation));

        Accommodation result = service.getAccommodationById(1L);

        assertNotNull(result);
        assertEquals("Sample Accommodation", result.getName());
    }

    @Test
    public void testSearchAccommodationsByCriteria() {
        AccommodationRepository repository = mock(AccommodationRepository.class);
        AccommodationService service = new AccommodationServiceImpl(repository);

        SearchCriteria criteria = SearchCriteria.builder()
                .keyword("Sample")
                .amenities(Arrays.asList("Swimming Pool", "Wi-Fi"))
                .tags(Arrays.asList("Beachfront", "Luxury"))
                .build();

        Pageable pageable = Pageable.unpaged();

        List<Accommodation> mockAccommodations = Collections.singletonList(
                Accommodation.builder()
                        .name("Sample Accommodation")
                        .description("This is a sample accommodation for testing purposes.")
                        .price(100.0)
                        .amenities(Collections.singletonList(
                                Amenity.builder().name("Swimming Pool").build()
                        ))
                        .tags(Collections.singletonList(
                                Tag.builder().tagName("Beachfront").build()
                        ))
                        .build()
        );
        Page<Accommodation> mockPage = new PageImpl<>(mockAccommodations);
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

        Page<Accommodation> result = service.searchAccommodationsByCriteria(criteria, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

}

