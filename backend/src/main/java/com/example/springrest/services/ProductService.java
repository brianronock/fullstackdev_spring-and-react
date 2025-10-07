package com.example.springrest.services;

import com.example.springrest.models.Product;
import com.example.springrest.repositories.ProductRepo;
import com.example.springrest.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.function.Consumer;


/**
 * Application service encapsulating business operations for {@link Product}s.
 *
 * <p><strong>Responsibilities</strong>: </p>
 * <ul>
 *   <li>Read operations with pagination &amp; search.</li>
 *   <li>Create, update, delete with transactional safety.</li>
 *   <li>Consistent "not found" behavior via {@link #getOrThrow(Long)}.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * // Controller example
 * @GetMapping("/products")
 * Page<ProductResponse> list(Pageable pageable) {
 *   return productService.list(pageable).map(mapper::toResponse);
 * }
 *
 * @PatchMapping("/products/{id}")
 * ProductResponse patch(@PathVariable Long id, @RequestBody ProductRequest req) {
 *   Product updated = productService.update(id, prod -> mapper.updateEntity(prod, req));
 *   return mapper.toResponse(updated);
 * }
 * }</pre>
 *
 * <h2>Transactional rules</h2>
 * <ul>
 *   <li>Write methods ({@link #create(Product)}, {@link #update(Long, Consumer)}, {@link #delete(Long)})
 *       are annotated {@link Transactional @Transactional} to ensure atomicity.</li>
 *   <li>Read methods are non-transactional by default for better throughput.</li>
 * </ul>
 *
 * <p><strong>Implementation note:</strong> This service is stateless and thread-safe under typical Spring usage. Avoid holding JPA entities
 *           between calls; always load and save within a transaction boundary. </p>
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;

    /**
     * Returns a paginated/sorted view of all products.
     *
     * @param pageable pagination and sorting information (page number, size, sort)
     * @return a page of products (possibly empty)
     */
    public Page<Product> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /**
     * Searches for products by name (case-insensitive), paged.
     *
     * @param q the query string to match against product names
     * @param pageable pagination and sorting information
     * @return a page of products matching the search criteria (possibly empty)
     */
    public Page<Product> searchByName(String q, Pageable pageable) {
        return repo.findByNameContainingIgnoreCase(q, pageable);
    }

    /**
     * Retrieves a Product by its ID or throws an exception if not found.
     *
     * @param id the ID of the product to retrieve
     * @return the product with the given ID
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    public Product getOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Creates/Persists a new product
     *
     * @param p the product to create (id must be {@code null})
     * @return the persisted product with generated ID
     */
    @Transactional
    public Product create(Product p) {
        return repo.save(p);
    }

    /**
     * Applies a mutation function to an existing product and persists the change.
     *
     * <p><strong>Pattern</strong>: pass a lambda that updates only what you intend to change.
     * Combine this with a mapper's partial update for PATCH-like flows.</p>
     *
     * <pre>{@code
     * productService.update(id, prod -> {
     *   prod.setName("New Name");
     *   prod.setPrice(new BigDecimal("19.99"));
     * });
     * }</pre>
     *
     * @param id      target product id
     * @param mutator mutation to apply to the loaded entity
     * @return the updated, persisted product
     * @throws ResourceNotFoundException if the id does not exist
     */
    @Transactional
    public Product update(Long id, Consumer<Product> mutator){
        Product existing = getOrThrow(id);
        mutator.accept(existing);
        return repo.save(existing);
    }

    /**
     * Deletes a product by its ID
     *
     * <p>Idempotent from the controller perspective: attempts to delete a non-existent ID
     * will result in {@link ResourceNotFoundException}, which is mapped to HTTP 404.</p>
     *
     * @param id the ID of the product to delete
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Transactional
    public void delete(Long id) {
        Product existing = getOrThrow(id);
        repo.delete(existing);
    }
}
