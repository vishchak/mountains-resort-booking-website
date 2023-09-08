package com.gmail.vishchak.denis.resortbooking.controller;

import com.gmail.vishchak.denis.resortbooking.apiresponse.ApiResponse;
import com.gmail.vishchak.denis.resortbooking.dto.AccommodationDTO;
import com.gmail.vishchak.denis.resortbooking.mappers.AccommodationMapper;
import com.gmail.vishchak.denis.resortbooking.model.Accommodation;
import com.gmail.vishchak.denis.resortbooking.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{accommodationId}")
    public ResponseEntity<ApiResponse<AccommodationDTO>> getAccommodationById(@PathVariable Long accommodationId) {
        Accommodation accommodation = accommodationService.getAccommodationById(accommodationId);
        AccommodationDTO responseDto = AccommodationMapper.mapToDto(accommodation);

        ApiResponse<AccommodationDTO> response = new ApiResponse<>(true, responseDto, "Accommodation with id" + accommodationId + "retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AccommodationDTO>> createAccommodation(
            @Valid @RequestBody AccommodationDTO request,
            BindingResult bindingResult
    ) {
        Accommodation accommodation = AccommodationMapper.mapToObject(request);
        accommodationService.createAccommodation(accommodation, bindingResult);

        AccommodationDTO responseDto = AccommodationMapper.mapToDto(accommodation);

        ApiResponse<AccommodationDTO> response = new ApiResponse<>(
                true,
                responseDto,
                "Accommodation with id " + accommodation.getId() + " has been added successfully"
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AccommodationDTO>> updateAccommodation(
            @PathVariable Long id,
            @Valid @RequestBody AccommodationDTO request,
            BindingResult bindingResult
    ) {
        Accommodation accommodation = AccommodationMapper.mapToObject(request);
        accommodationService.updateAccommodation(id, accommodation, bindingResult);

        AccommodationDTO responseDto = AccommodationMapper.mapToDto(accommodation);

        ApiResponse<AccommodationDTO> response = new ApiResponse<>(
                true,
                responseDto,
                "Accommodation with id " + id + " has been updated successfully"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodation(id);
        ApiResponse<Void> response = new ApiResponse<>(
                true,
                null,
                "Accommodation with id " + id + " has been deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
}