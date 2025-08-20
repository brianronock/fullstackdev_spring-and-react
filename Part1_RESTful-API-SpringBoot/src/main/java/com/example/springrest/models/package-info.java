/**
 * JPA Entities that represent the core domain model of the application.
 *
 * <p>Entities are classes mapped to database tables using JPA annotations
 * (such as {@link jakarta.persistence.Entity}, {@link jakarta.persistence.Table},
 * and {@link jakarta.persistence.Column}). They define the <b>persistent state</b>
 * of the system.</p>
 *
 * <p>Key characteristics of entities:</p>
 * <ul>
 *   <li><b>Persistence mapping:</b> Each entity corresponds to a table row,
 *   and each field maps to a database column.</li>
 *
 *   <li><b>Identifiers:</b> Entities usually have a primary key
 *   (e.g., {@code @Id Long id}) to uniquely identify each record.</li>
 *
 *   <li><b>Relationships:</b> Entities may define relationships
 *   (e.g., {@code @OneToMany}, {@code @ManyToOne}) between tables.</li>
 *
 *   <li><b>Business rules:</b> Some validation or domain rules may live here,
 *   though complex logic is often delegated to the service layer.</li>
 * </ul>
 *
 * <p><b>Important:</b> Entities should generally <i>not</i> be exposed directly
 * in API endpoints. Instead, use DTOs from the {@code dto} package to define
 * the contract with external clients. This avoids leaking internal details,
 * improves security, and allows flexibility in evolving the API.</p>
 *
 * <p>Example in this package: {@link com.example.springrest.models.Product}</p>
 */
package com.example.springrest.models;
