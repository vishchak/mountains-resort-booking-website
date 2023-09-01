package com.gmail.vishchak.denis.resortbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    @JsonIgnore
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonIgnore
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;
}
