package com.offlix.offlixstore.request;

import com.offlix.offlixstore.model.Category;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;

    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
