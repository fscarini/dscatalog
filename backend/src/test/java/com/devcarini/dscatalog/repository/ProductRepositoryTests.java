package com.devcarini.dscatalog.repository;

import com.devcarini.dscatalog.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        Long existingId = 1L;

        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = new Product(
                1L,
                "Iphone 18 Pro do Senado",
                "Com o dinheiro do povo, todo o parlamento terá seu iphone!",
                2600000.0,
                "daviAlcolumbreDeVolvo"
        );

        product.setId(null);
        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(11, product.getId());

    }

    @Test
    public void findByIdShouldReturnNotEmptyOptionalWhenIdExists(){
        Long existingId = 1L;
        Optional<Product> result = repository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdSIsNull(){
        Long nonExistingId = 11L;
        Optional<Product> result = repository.findById(nonExistingId);

        Assertions.assertFalse(result.isPresent());
    }

}
