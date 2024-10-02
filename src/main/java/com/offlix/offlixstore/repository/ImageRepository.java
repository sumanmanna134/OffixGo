package com.offlix.offlixstore.repository;

import com.offlix.offlixstore.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
