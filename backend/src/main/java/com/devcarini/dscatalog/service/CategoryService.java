package com.devcarini.dscatalog.service;

import com.devcarini.dscatalog.dto.CategoryDTO;
import com.devcarini.dscatalog.entity.Category;
import com.devcarini.dscatalog.repository.CategoryRepository;
import com.devcarini.dscatalog.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable){
        Page<Category> result = categoryRepository.findAll(pageable);
        return result.map(x -> new CategoryDTO(x.getId(), x.getName()));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category result = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));
        return new CategoryDTO(result);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.name());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

}
