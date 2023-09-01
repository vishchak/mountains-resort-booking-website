package com.gmail.vishchak.denis.resortbooking.repository;

import com.gmail.vishchak.denis.resortbooking.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
