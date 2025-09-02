package com.example.springrest.services;

import com.example.springrest.exceptions.ResourceNotFoundException;
import com.example.springrest.models.Product;
import com.example.springrest.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ProductService}.
 *
 * <p><strong>Responsibilities tested</strong>:
 * <ul>
 *   <li>Ensures that {@code getOrThrow} correctly raises a
 *       {@link ResourceNotFoundException}
 *       when a product is not found in the repository.</li>
 *   <li>Verifies that {@code update} applies field changes
 *       (e.g., updating a product’s price) and persists them via the repository.</li>
 * </ul>
 *
 * <p><strong>Testing strategy</strong>:
 * <ul>
 *   <li>Uses Mockito to mock {@link ProductRepo}
 *       and inject it into the service under test.</li>
 *   <li>Focuses purely on service logic, isolated from database or web concerns.</li>
 *   <li>Asserts both behavior (exceptions thrown) and state changes
 *       (updated price, repository interactions).</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepo repo;
    @InjectMocks
    ProductService service;

    @Test
    void getOrThrowsIfNotFound() {
        when(repo.findById(42L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getOrThrow(42L));
    }

    @Test
    void updateChangesFields() {
        Product existing = new Product("Old", BigDecimal.valueOf(5));
        existing.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        service.update(1L, p -> p.setPrice(BigDecimal.TEN));
        assertEquals(BigDecimal.TEN, existing.getPrice());
        verify(repo).save(existing);
    }
}
