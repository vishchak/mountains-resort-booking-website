package com.gmail.vishchak.denis.resortbooking.model;

import jakarta.persistence.*;
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
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Accommodation> accommodations;

    @ManyToMany(mappedBy = "tags")
    private List<Restaurant> restaurants;

    @ManyToMany(mappedBy = "tags")
    private List<Activity> activities;
}