package com.devcarini.dscatalog.factory;

import com.devcarini.dscatalog.dto.ProductDTO;
import com.devcarini.dscatalog.entity.Category;
import com.devcarini.dscatalog.entity.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(
                30L,
                "Produto de Fábrica",
                "Lorem ipslum",
                27.90,
                "https://github.com/fscarini"
        );
        product.getCategories().add(new Category(1L, "Construção Civil"));
        return product;
    }

    public static ProductDTO createProductDTO(Product entity){
        return new ProductDTO(entity);
    }

    public static Category createCategory(){
        return new Category(1L, "Construção Civil");
    }

}
