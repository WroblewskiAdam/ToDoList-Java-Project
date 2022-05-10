package com.example.PAP2022.repository;

import com.example.PAP2022.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {
}
