package com.gmail.vishchak.denis.resortbooking.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
    private List<String> amenities;
    private List<String> tags;
}

