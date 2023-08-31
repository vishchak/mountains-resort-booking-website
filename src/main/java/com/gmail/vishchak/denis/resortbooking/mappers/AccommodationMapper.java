package com.gmail.vishchak.denis.resortbooking.mappers;

import com.gmail.vishchak.denis.resortbooking.dto.AccommodationDTO;
import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class AccommodationMapper {
    public static AccommodationDTO mapToDto(Accommodation accommodation) {
        return AccommodationDTO.builder()
                .name(accommodation.getName())
                .description(accommodation.getDescription())
                .price(accommodation.getPrice())
                .images(accommodation.getImages())
                .tags(accommodation.getTags())
                .amenities(accommodation.getAmenities())
                .promotions(accommodation.getPromotions())
                .build();
    }

    public static List<AccommodationDTO> mapPageToDtos(Page<Accommodation> accommodationPage) {
        return accommodationPage.stream()
                .map(AccommodationMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
