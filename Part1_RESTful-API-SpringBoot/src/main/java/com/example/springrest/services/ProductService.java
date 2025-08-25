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
 * Service layer for managing {@link Product} entities.
 * <p>
 *     This class encapsulates business logic for working with products, including listing, searching,
 *     creating, and deleting. It acts as an intermediary between the controllers and the {@link ProductRepo}
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Provide paginated access to products.</li>
 *     <li>Support case-insensitive search by product name.</li>
 *     <li>Handle "not found" scenarios using {@link ResourceNotFoundException}.</li>
 *     <li>Ensure transactional consistency for create, update, and delete operations.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *     <li>Methods that modify the database are annotated with {@link Transactional} to
 *     ensure atomic operations.</li>
 *     <li>Consumers are used for flexible mutation during updates</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;

    /**
     * Returns a pageable list of all products.
     * @param pageable pagination and sorting information
     * @return a page of products
     */
    public Page<Product> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    /**
     * Searches for products by name, ignoring case sensitivity.
     * @param q the query string to match against product names
     * @param pageable pagination and sorting information
     * @return a page of products matching the search criteria
     */
    public Page<Product> searchByName(String q, Pageable pageable) {
        return repo.findByNameContainingIgnoreCase(q, pageable);
    }

    /**
     * Retrieves a Product by its ID or throws an exception if not found.
     * @param id the ID of the product to retrieve
     * @return the product with the given ID
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    public Product getOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Creates a new product
     * @param p the product to create
     * @return the persisted product
     */
    @Transactional
    public Product create(Product p) {
        return repo.save(p);
    }

    /**
     * Updates an existing product with the given IS by applying a mutator function.
     * @param id the ID of the product to update
     * @param mutator a function that modifies the existing product
     * @return the updated product
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Transactional
    public Product update(Long id, Consumer<Product> mutator){
        Product existing = getOrThrow(id);
        mutator.accept(existing);
        return repo.save(existing);
    }

    /**
     * Deletes a product by its ID
     * @param id the ID of the product to delete
     * @throws ResourceNotFoundException if no product exists with the given ID
     */
    @Transactional
    public void delete(Long id) {
        Product existing = getOrThrow(id);
        repo.delete(existing);
    }
}
