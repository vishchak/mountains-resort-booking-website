package com.gmail.vishchak.denis.resortbooking.dto;

import com.gmail.vishchak.denis.resortbooking.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private String name;
    private String description;
    private Double price;
    private List<Image> images = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();

    private List<Amenity> amenities = new ArrayList<>();

    private List<Promotion> promotions = new ArrayList<>();
}
