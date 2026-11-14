package com.devcarini.dscatalog.services;

import com.devcarini.dscatalog.dto.ProductDTO;
import com.devcarini.dscatalog.entity.Category;
import com.devcarini.dscatalog.entity.Product;
import com.devcarini.dscatalog.factory.Factory;
import com.devcarini.dscatalog.repository.CategoryRepository;
import com.devcarini.dscatalog.repository.ProductRepository;
import com.devcarini.dscatalog.service.ProductService;
import com.devcarini.dscatalog.service.exceptions.DatabaseException;
import com.devcarini.dscatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO dto;
    private Category category;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 3L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));
        dto = Factory.createProductDTO(product);
        category = Factory.createCategory();
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists(){
        Mockito.when(productRepository.findById(existingId))
                .thenReturn(Optional.of(product));

        ProductDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(product.getId(), result.id());
        Assertions.assertEquals(product.getName(), result.name());

        Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExists(){
        Mockito.when(productRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
    }

    @Test
    public void findAllShouldReturnPage(){
        // Simula o comportamento do findAll
        Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);

        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists(){
        Mockito.when(productRepository.getReferenceById(existingId))
                .thenReturn(product);

        Mockito.when(categoryRepository.getReferenceById(existingId))
                .thenReturn(category);

        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(product);

        ProductDTO result = service.update(dto, existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto.name(), result.name());
        Assertions.assertEquals(dto.description(), result.description());
        Assertions.assertEquals(dto.price(), result.price());
        Assertions.assertEquals(dto.imgUrl(), result.imgUrl());

        Mockito.verify(productRepository, Mockito.times(1))
                .getReferenceById(existingId);

        Mockito.verify(productRepository, Mockito.times(1))
                .save(Mockito.any(Product.class));
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
        Mockito.when(productRepository.getReferenceById(nonExistingId))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(dto, nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(1))
                .getReferenceById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Mockito.when(productRepository.existsById(existingId))
                .thenReturn(true);

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){

        Mockito.when(productRepository.existsById(nonExistingId))
                .thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
           service.delete(nonExistingId);
        });

        Mockito.verify(productRepository, Mockito.times(0)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId(){

        Mockito.when(productRepository.existsById(dependentId))
                .thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository)
                .deleteById(dependentId);

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
    }

}
