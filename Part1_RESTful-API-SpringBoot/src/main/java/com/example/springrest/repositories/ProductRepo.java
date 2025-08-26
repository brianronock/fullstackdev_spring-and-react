package com.example.springrest.repositories;

import com.example.springrest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for {@link Product} entities.
 *
 * <p>Inherits CRUD, pagination, and sorting from {@link JpaRepository} and adds
 * a simple case-insensitive name search.</p>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Page through all products, newest first
 * Page<Product> page = repo.findAll(PageRequest.of(0, 20, Sort.by(DESC, "id")));
 *
 * // Search by name, case-insensitive
 * Page<Product> mugs = repo.findByNameContainingIgnoreCase("mug", PageRequest.of(0, 10));
 * }</pre>
 *
 * <p><strong>Performance tips</strong>:
 * <ul>
 *   <li>Consider an index on {@code lower(name)} for large catalogs to speed up case-insensitive matching.</li>
 *   <li>Always pass a {@link Pageable} to avoid loading large result sets into memory.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 */
public interface ProductRepo extends JpaRepository<Product, Long> {
    /**
     * Finds products whose {@code name} contains the given keyword, ignoring case sensitivity; paged.
     *
     * @param name the substring to search for within product names (case-insensitive)
     * @param pageable the pagination and sorting information; never {@code null}
     * @return a page of products matching the search criteria; never {@code null}
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
