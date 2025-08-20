package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO representing the data returned to clients for a product.
 * <p>
 *     This record is used in API responses. It ensures that only safe,
 *     client-relevant product data is exposed, decoupled from internal entities.
 * </p>
 */
@Schema(description = "Request payload for creating or updating a product.")
public record ProductRequest(
        @Schema(
                description = "The name of the product, must not be blank and upto 120 characters.",
                example = "Coffee Mug",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Name is mandatory")
        @Size(max = 120, message = "Name must be at most 120 characters")
        String name,

        @Schema(
                description = "The price of the product. Must be greater than 0 and include upto 2 decimal places.",
                example = "999.99",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Price must be provided")
        @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
        BigDecimal price
) {}
