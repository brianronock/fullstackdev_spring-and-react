/**
 * Provides MapStruct-based mappers for converting between
 * domain entities and Data Transfer Objects (DTOs).
 *
 * <p>Each mapper defines methods to:
 * <ul>
 *   <li>Convert incoming {@code ProductRequest} DTOs into {@code Product} entities.</li>
 *   <li>Convert {@code Product} entities into outgoing {@code ProductResponse} DTOs.</li>
 *   <li>Update existing entities selectively (ignoring {@code null} values).</li>
 * </ul>
 *
 * <p>By using MapStruct, boilerplate field mapping code is generated at compile time,
 * ensuring type safety, readability, and performance.
 *
 * <p>All mappers are registered as Spring beans by specifying
 * {@code componentModel = "spring"} in their {@code @Mapper} annotation,
 * allowing them to be injected into services and controllers.
 */
package com.example.springrest.mappers;
