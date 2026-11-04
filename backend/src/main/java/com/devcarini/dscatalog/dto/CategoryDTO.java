package com.devcarini.dscatalog.dto;

import com.devcarini.dscatalog.entity.Category;

public record CategoryDTO(
        Long id,
        String name
) {

    public CategoryDTO(Category entity){
        this(
                entity.getId(),
                entity.getName()
        );
    }

}
