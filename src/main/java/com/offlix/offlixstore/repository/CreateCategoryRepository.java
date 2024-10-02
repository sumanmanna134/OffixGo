package com.offlix.offlixstore.repository;

import com.offlix.offlixstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreateCategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
