package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.stream.Collectors;

public record ProductDTO(
        Long id,
        @NotBlank(message = "Campo obrigatório")
        String name,
        @NotBlank(message = "Campo obrigatório")
        String description,
        @Positive(message = "O valor deve ser positivo")
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
