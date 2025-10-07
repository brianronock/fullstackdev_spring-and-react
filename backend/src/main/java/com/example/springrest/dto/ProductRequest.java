package com.example.springrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Request DTO used to create or update a {@code Product}.
 *
 * <p><strong>Why a DTO?</strong>
 * Keeps the HTTP contract stable and safe, decoupled from the JPA entity.
 * Validation annotations ensure early, actionable feedback for clients.
 * </p>
 *
 * <p><strong>Example (JSON)</strong>:</p>
 * <pre>{@code
 * {
 *   "name": "Coffee Mug",
 *   "price": 12.99
 * }
 * }</pre>
 *
 * <p><strong>Usage (Controller)</strong>:</p>
 * <pre>{@code
 * @PostMapping("/products")
 * public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
 *     Product saved = productService.create(new Product(req.name(), req.price()));
 *     return ResponseEntity
 *         .created(URI.create("/products/" + saved.getId()))
 *         .body(new ProductResponse(saved.getId(), saved.getName(), saved.getPrice()));
 * }
 * }</pre>
 *
 * @since 1.0
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
