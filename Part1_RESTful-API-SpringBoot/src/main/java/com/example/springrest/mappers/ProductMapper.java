package com.example.springrest.mappers;

import com.example.springrest.dto.ProductRequest;
import com.example.springrest.dto.ProductResponse;
import com.example.springrest.models.Product;
import org.mapstruct.*;

/**
 * Maps between API-layer DTOs and the {@link Product} JPA entity.
 * <p>
 *     MapStruct generates a concrete implementation at build time
 *     (e.g. {@code ProductMapperImpl}). Because {@code componentModel="spring"},
 *     the generated mapper is a Spring bean and can be injected where needed.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    /**
     * Creates a new {@link Product} entity from an incoming API request
     * @param dto validated request payload containing user-provided fields
     * @return a new {@link Product} with fields copied from {@code dto} and a {@code null ID}
     */
    Product toEntity(ProductRequest dto);

    /**
     * Creates a {@link ProductResponse} DTO suitable for API responses.
     * @param entity a managed {@link Product} entity
     * @return a value DTO reflecting the entity's current state
     */
    ProductResponse toResponse(Product entity);

    /**
     * Updates an existing {@link Product} in-place from a {@link ProductRequest}.
     * Fields that are {@code null} in {@code source} are <em>ignored</em> and do not overwrite
     * non-null values in {@code target}. This makes the method safe for PATCH-like flows as well
     * as PUT flows where validation guarantees non-null fields.
     * @param target the entity to mutate (usually loaded from the database)
     * @param source the incoming request containing new values
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Product target, ProductRequest source);
}
