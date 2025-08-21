/**
 * Application root package for the Spring Boot backend.
 *
 * <p>This package contains the {@code @SpringBootApplication} entry point and
 * serves as the component-scan root for the entire project. All subpackages
 * (controllers, service, repository, models, dto, config) are discovered
 * automatically when the main application class is placed here.</p>
 *
 * <p>Package layout overview:</p>
 * <ul>
 *   <li>{@code controllers} – REST controllers (web layer)</li>
 *   <li>{@code service} – business logic/services</li>
 *   <li>{@code repository} – Spring Data JPA repositories</li>
 *   <li>{@code models} – JPA entities (domain/persistence layer)</li>
 *   <li>{@code dto} – request/response data transfer objects</li>
 *   <li>{@code config} – application and web configuration (CORS, OpenAPI, etc.)</li>
 *   <li>{@code exception} – global exception handlers and custom exceptions</li>
 * </ul>
 *
 * <p><b>Note:</b> Keep the main application class (e.g.,
 * {@code com.example.springrest.SpringRestApplication}) in this package
 * to ensure Spring’s component scan picks up all subpackages.</p>
 */
package com.example.springrest;
