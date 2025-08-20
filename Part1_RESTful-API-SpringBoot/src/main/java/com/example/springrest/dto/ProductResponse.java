package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * DTO representing the data returned to clients for a product.
 * <p>
 *     This record is used in API responses. It ensures that only safe,
 *     client-relevant product data is exposed, decoupled from internal entities.
 * </p>
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
