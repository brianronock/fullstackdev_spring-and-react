/**
 * This package contains the repository interfaces for the application.
 * <p>
 * Repositories are responsible for abstracting persistence and providing
 * a simple interface for data access. They extend Spring Data JPA's
 * {@link org.springframework.data.jpa.repository.JpaRepository} to gain
 * built-in CRUD operations and support for pagination and sorting.
 * </p>
 *
 * <h2>Current Repositories</h2>
 * <ul>
 *   <li>{@link com.example.springrest.repositories.ProductRepo} — Provides
 *       CRUD operations and custom queries for {@link com.example.springrest.models.Product}
 *       entities, including case-insensitive name search with pagination.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Repositories should remain thin - business logic belongs in the service layer.</li>
 *   <li>Custom queries can be defined using Spring Data's derived query methods or JPQL.</li>
 * </ul>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.example.springrest.models.Product
 */
package com.example.springrest.repositories;
