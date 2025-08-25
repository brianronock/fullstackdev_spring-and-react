package com.example.springrest.repositories;

import com.example.springrest.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations and custom queries on {@link Product} entities.
 * <p>
 *     This interface extends {@link JpaRepository}, inheriting basic operations such as saving,
 *     deleting, and finding entities by their ID.
 * </p>
 *
 * <h2>Custom Query</h2>
 * <ul>
 *     <li>
 *         {@link #findByNameContainingIgnoreCase(String, Pageable)} - Retrieves a paginated list
 *         of products whose names contain the specified keyword, ignoring case sensitivity.
 *     </li>
 * </ul>
 */
public interface ProductRepo extends JpaRepository<Product, Long> {
    /**
     * Finds products whose name contains the given keyword, ignoring case sensitivity.
     * @param name the substring to search for within product names (case-insensitive)
     * @param pageable the pagination and sorting information
     * @return a page of products matching the search criteria
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
