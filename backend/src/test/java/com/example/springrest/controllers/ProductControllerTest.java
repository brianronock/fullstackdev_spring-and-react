package com.example.springrest.controllers;

import com.example.springrest.mappers.ProductMapperImpl;
import com.example.springrest.models.Product;
import com.example.springrest.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for {@link ProductController} using Spring's {@link WebMvcTest} support.
 *
 * <p><strong>Test strategy:</strong>
 * <ul>
 *   <li>Start only the web layer (no database or services).</li>
 *   <li>Inject a {@link MockMvc} client to simulate HTTP calls.</li>
 *   <li>Replace the real {@link ProductService} with a Mockito mock using {@link MockBean}.</li>
 *   <li>Import the {@link ProductMapperImpl} so that real mapping logic is used in tests.</li>
 * </ul>
 *
 * <p><strong>Note on @MockBean:</strong>
 * In Spring Boot 3.5+, {@link MockBean} is marked <em>deprecated</em>.
 * It still works, but the Spring team is encouraging a shift toward
 * alternative mocking approaches (e.g., plain Mockito with manual injection,
 * or newer testing features like {@code @ServiceTest} in future releases).
 * For the purposes of this book (Part 1), we continue to use {@link MockBean}
 * for its simplicity. We will revisit alternatives in Part 3 when covering
 * advanced testing strategies.
 *
 * <p><strong>Covered cases:</strong>
 * <ul>
 *   <li>{@link #getByIdReturnsProduct()} — verifies that GET by ID returns the expected JSON.</li>
 *   <li>{@link #createValidationFail()} — verifies that invalid input is rejected with 400 and error details.</li>
 * </ul>
 */
@WebMvcTest(ProductController.class)
@Import(ProductMapperImpl.class)
public class ProductControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ProductService service;

    @Test
    void getByIdReturnsProduct() throws Exception {
        Product prod = new Product("X", BigDecimal.ONE);
        prod.setId(100L);
        when(service.getOrThrow(100L)).thenReturn(prod);
        mvc.perform(get("/api/products/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("X"));
    }

    @Test
    void createValidationFail() throws Exception {
        String json = "{\"name\": \"\", \"price\": 0}";
        mvc.perform(post("/api/products").contentType("application/json").content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists());
    }
}
