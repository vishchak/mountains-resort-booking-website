package com.gmail.vishchak.denis.resortbooking.controller;

import com.gmail.vishchak.denis.resortbooking.apiresponse.ApiResponse;
import com.gmail.vishchak.denis.resortbooking.dto.AccommodationDTO;
import com.gmail.vishchak.denis.resortbooking.mappers.AccommodationMapper;
import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accommodation")
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AccommodationDTO>>> getAllAccommodations(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "4") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Accommodation> accommodations = accommodationService.getAllAccommodations(pageable);
        List<AccommodationDTO> responseDtos = AccommodationMapper.mapPageToDtos(accommodations);

        ApiResponse<List<AccommodationDTO>> response = new ApiResponse<>(true, responseDtos, "Accommodations retrieved successfully");
        return ResponseEntity.ok(response);
    }
}
