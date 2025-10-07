package com.example.springrest.repositories;

import com.example.springrest.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for {@link ProductRepo}.
 *
 * <p><strong>Responsibilities tested</strong>:
 * <ul>
 *   <li>Verifies that case-insensitive search works as expected using
 *       {@code findByNameContainingIgnoreCase}.</li>
 *   <li>Ensures substring search returns matching results.</li>
 * </ul>
 *
 * <p><strong>Testing strategy</strong>:
 * <ul>
 *   <li>Uses Spring Boot’s {@link DataJpaTest}
 *       annotation to bootstrap only JPA components with an in-memory H2 database.</li>
 *   <li>Persists test data into the repository, then queries it with different search inputs.</li>
 *   <li>Asserts that results match expectations regardless of case or partial strings.</li>
 * </ul>
 *
 * <p>By default, tests are transactional and rolled back after each method,
 * keeping the database clean between tests.</p>
 */
@DataJpaTest
class ProductRepoTest {

    @Autowired
    ProductRepo repo;

    @Test
    void searchByNameIgnoreCase() {
        repo.save(new Product("TestProduct", BigDecimal.TEN));
        repo.save(new Product("Another", BigDecimal.ONE));

        Page<Product> page = repo.findByNameContainingIgnoreCase("testproduct", PageRequest.of(0, 10));
        assertEquals(1, page.getTotalElements());
        Product result = page.getContent().get(0);
        assertEquals("TestProduct", result.getName());

        Page<Product> page2 = repo.findByNameContainingIgnoreCase("tes", PageRequest.of(0, 10));
        assertEquals(1, page2.getTotalElements());
    }


}
