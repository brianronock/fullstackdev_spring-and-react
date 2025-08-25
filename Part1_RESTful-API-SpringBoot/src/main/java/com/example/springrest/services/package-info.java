/**
 * This package contains the service layer of the application.
 * <p>
 * Services act as the business logic layer, sitting between controllers
 * and repositories. They orchestrate application workflows, enforce rules,
 * and delegate persistence to the repository layer.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Implement business logic that is too complex for controllers or repositories.</li>
 *   <li>Encapsulate transactional boundaries using Spring's {@code @Transactional} annotation.</li>
 *   <li>Coordinate between multiple repositories when necessary.</li>
 *   <li>Serve as the entry point for unit tests of business rules.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Keep services cohesive — one service per aggregate or domain concept.</li>
 *   <li>Controllers should only call services, never repositories directly.</li>
 *   <li>Ensure business logic is not duplicated across controllers.</li>
 * </ul>
 *
 * @see com.example.springrest.repositories
 * @see org.springframework.transaction.annotation.Transactional
 */
package com.example.springrest.services;
