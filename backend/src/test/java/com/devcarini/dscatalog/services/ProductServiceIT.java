package com.devcarini.dscatalog.services;

import com.devcarini.dscatalog.dto.ProductDTO;
import com.devcarini.dscatalog.repositories.ProductRepository;
import com.devcarini.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = 10L;
    }

    @Test
    public void findAllShouldReturnPageWhenFirstPageIsNotEmpty(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAll(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(result.getNumber(), pageRequest.getPageNumber());
        Assertions.assertEquals(result.getSize(), pageRequest.getPageSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void findAllShouldReturnEmptyPageWhenPageDoesNotExist(){
        PageRequest pageRequest = PageRequest.of(32, 10);
        Page<ProductDTO> result = service.findAll(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
        Page<ProductDTO> result = service.findAll(pageRequest);
        List<String> names = result.getContent().stream()
                .map(ProductDTO::name)
                .toList();
        List<String> sortedNames = names.stream()
                .sorted()
                .toList();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertTrue(result.getContent().size() <= pageRequest.getPageSize());
        Assertions.assertEquals(result.getNumber(), pageRequest.getPageNumber());
        Assertions.assertEquals(sortedNames, names);
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(nonExistingId));
    }

}
