package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Response DTO returned to clients for {@code Product} resources.
 *
 * <p><strong>Shape</strong>: minimal, stable fields optimized for clients.
 * Avoids leaking internal entity details and supports versioned evolution.
 * </p>
 *
 * <p><strong>Example (JSON)</strong>:</p>
 * <pre>{@code
 * {
 *   "id": 42,
 *   "name": "Coffee Mug",
 *   "price": 12.99
 * }
 * }</pre>
 *
 * <p><strong>Usage (Service → Controller)</strong>:</p>
 * <pre>{@code
 * Product p = productService.getOrThrow(id);
 * return new ProductResponse(p.getId(), p.getName(), p.getPrice());
 * }</pre>
 *
 * @since 1.0
 */
@Schema(description = "Response payload containing product details.")
public record ProductResponse(
        @Schema(description = "The unique ID of the product.", example = "1")
        Long id,

        @Schema(description = "The name of the product.", example = "Coffee Mug")
        String name,

        @Schema(description = "The price of the product.", example = "999.99")
        BigDecimal price
) {}
