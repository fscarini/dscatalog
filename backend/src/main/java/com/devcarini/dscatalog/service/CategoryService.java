package com.devcarini.dscatalog.service;

import com.devcarini.dscatalog.dto.CategoryDTO;
import com.devcarini.dscatalog.entity.Category;
import com.devcarini.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<CategoryDTO> findAll(Pageable pageable){
        Page<Category> result = categoryRepository.findAll(pageable);
        return result.map(x -> new CategoryDTO(x.getId(), x.getName()));
    }

}
