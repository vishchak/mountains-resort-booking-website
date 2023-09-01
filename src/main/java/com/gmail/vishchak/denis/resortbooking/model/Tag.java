package com.gmail.vishchak.denis.resortbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Accommodation> accommodations;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Restaurant> restaurants;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Activity> activities;
}