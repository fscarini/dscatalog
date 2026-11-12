package com.devcarini.dscatalog.services;

import com.devcarini.dscatalog.repository.ProductRepository;
import com.devcarini.dscatalog.service.ProductService;
import com.devcarini.dscatalog.service.exceptions.DatabaseException;
import com.devcarini.dscatalog.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long dependentId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        dependentId = 3L;
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
