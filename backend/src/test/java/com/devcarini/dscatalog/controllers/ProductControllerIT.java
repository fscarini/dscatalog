package com.devcarini.dscatalog.controllers;

import com.devcarini.dscatalog.dto.ProductDTO;
import com.devcarini.dscatalog.factory.Factory;
import com.devcarini.dscatalog.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.StreamSupport;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductService productService;

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
    public void findAllShouldSortedPagedWhenSortByName() throws Exception{
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/products?page=0&size=12&sort=name,asc")
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(countTotalProducts))
                .andReturn();

        JsonNode content = mapper.readTree(
                result.getResponse().getContentAsString()
        ).get("content");

        List<String> names = StreamSupport.stream(content.spliterator(), false)
                .map(node -> node.get("name").asText())
                .toList();

        List<String> sortedNames = names.stream()
                .sorted()
                .toList();

        Assertions.assertEquals(sortedNames, names);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        ProductDTO dto = Factory.createProductDTO();
        String jsonBody = mapper.writeValueAsString(dto);
        String expectedName = dto.name();

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/{id}", existingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedName));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ProductDTO dto = Factory.createProductDTO();
        String jsonBody = mapper.writeValueAsString(dto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/{id}", nonExistingId)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
