/**
 * REST controllers that define the public API of the application.
 *
 * <p>
 *     Controllers are the entry point for HTTP requests. They expose
 *     CRUD operations on domain entities (like {@code Product}) as REST endpoints.
 *     Each controller delegates business logic to the service layer
 *     and transforms entities into DTOs for client-safe responses.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Map HTTP requests to service calls using {@code @RequestMapping} and its variants.</li>
 *   <li>Apply validation on request bodies and parameters (e.g., {@code @Valid}, {@code @Min}).</li>
 *   <li>Return appropriate response entities with status codes and payloads.</li>
 *   <li>Document endpoints with Swagger/OpenAPI annotations.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>
 *     Parameter-level validations (e.g., {@code @Min} on IDs) are shown in early chapters
 *     but will only be fully enforced when {@code @Validated} and method validation mare introduced in <em>Part III</em>.
 *   </li>
 *   <li>
 *     Exception handling is not done directly in controllers — instead,
 *     {@link com.example.springrest.exceptions.GlobalExceptionHandler}
 *     centralizes error handling for consistency.
 *   </li>
 * </ul>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * GET /api/products?page=0&size=20
 * POST /api/products {"name":"Coffee Mug","price":12.99}
 * }</pre>
 */
package com.example.springrest.controllers;
