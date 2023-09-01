package com.gmail.vishchak.denis.resortbooking.repository;

import com.gmail.vishchak.denis.resortbooking.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
