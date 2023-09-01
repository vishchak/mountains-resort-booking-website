package com.gmail.vishchak.denis.resortbooking.repository;

import com.gmail.vishchak.denis.resortbooking.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
