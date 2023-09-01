package com.gmail.vishchak.denis.resortbooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    private Double price;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "accommodation")
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable(
            name = "accommodation_tags",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Size(min = 1, message = "At least one tag is required")
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "accommodation_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    @Size(min = 1, message = "At least one amenity is required")
    private List<Amenity> amenities;

    @ManyToMany(mappedBy = "accommodations")
    private List<Promotion> promotions;
}