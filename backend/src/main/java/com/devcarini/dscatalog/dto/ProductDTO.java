package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entity.Product;

import java.util.Set;
import java.util.stream.Collectors;

public record ProductDTO(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl,
        Set<CategoryDTO> categories
) {

    public ProductDTO(Product entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                entity.getCategories().stream()
                        .map(CategoryDTO::new)
                        .collect(Collectors.toSet())
        );
    }

}
