package com.devcarini.dscatalog.controller;

import com.devcarini.dscatalog.dto.ProductDTO;
import com.devcarini.dscatalog.entity.Product;
import com.devcarini.dscatalog.factory.Factory;
import com.devcarini.dscatalog.service.ProductService;
import com.devcarini.dscatalog.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ProductService service;

    private ProductDTO dto;
    private Product product;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        product = Factory.createProduct();
        dto = Factory.createProductDTO();
        page = new PageImpl<>(List.of(dto));
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
        Mockito.when(service.findById(existingId)).thenReturn(dto);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/{id}", existingId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products/{id}", nonExistingId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void insertShouldReturnCreated() throws Exception {
        Mockito.when(service.insert(dto)).thenReturn(dto);
        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/products")
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories").exists());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.eq(existingId)))
                .thenReturn(dto);
        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/{id}", existingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.when(service.update(ArgumentMatchers.any(), ArgumentMatchers.eq(nonExistingId)))
                .thenThrow(ResourceNotFoundException.class);

        String jsonBody = mapper.writeValueAsString(dto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        Mockito.doNothing().when(service).delete(existingId);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/{id}", existingId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
