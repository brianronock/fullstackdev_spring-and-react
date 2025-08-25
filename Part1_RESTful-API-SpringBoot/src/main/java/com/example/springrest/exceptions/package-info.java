/**
 * This package defines custom exceptions for the application.
 * <p>
 * Exceptions in this package extend standard Java or Spring exceptions
 * to represent domain-specific error conditions. They improve readability
 * and allow centralized error handling.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Provide meaningful exception types (e.g. {@code ProductNotFoundException}).</li>
 *   <li>Support fine-grained error handling and more descriptive API responses.</li>
 *   <li>Work in tandem with {@code @ControllerAdvice} and Spring's exception handling
 *       mechanisms to translate exceptions into HTTP responses.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Prefer runtime exceptions for domain errors to reduce boilerplate.</li>
 *   <li>Group related exceptions into this package for maintainability.</li>
 *   <li>Document exception behavior in service and controller methods.</li>
 * </ul>
 *
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 * @see org.springframework.http.ResponseEntity
 */
package com.example.springrest.exceptions;
