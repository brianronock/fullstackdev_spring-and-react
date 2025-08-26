package com.example.springrest.mappers;

import com.example.springrest.dto.ProductRequest;
import com.example.springrest.dto.ProductResponse;
import com.example.springrest.models.Product;
import org.mapstruct.*;


/**
 * Maps between API-layer DTOs and the {@link Product} JPA entity.
 *
 * <p><strong>How it works</strong>:
 * MapStruct generates an implementation at build time (e.g. {@code ProductMapperImpl}).
 * With {@code componentModel="spring"}, the mapper is registered as a Spring bean and can
 * be injected into controllers or services.</p>
 *
 * <h2>Design notes</h2>
 * <ul>
 *   <li><strong>toEntity</strong> is intended for <em>create</em> flows. The resulting entity has a {@code null} id.</li>
 *   <li><strong>toResponse</strong> is a read-side projection that keeps your HTTP contract decoupled from JPA.</li>
 *   <li><strong>updateEntity</strong> performs an in-place, <em>partial</em> update: <em>null</em> values in the
 *       source DTO are ignored (see {@link NullValuePropertyMappingStrategy#IGNORE}). This makes it safe for PATCH-like flows.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Create flow (Controller)
 * @PostMapping("/products")
 * ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
 *   Product entity = mapper.toEntity(req);
 *   Product saved = productService.create(entity);
 *   return ResponseEntity
 *      .created(URI.create("/products/" + saved.getId()))
 *      .body(mapper.toResponse(saved));
 * }
 *
 * // Partial update (Service)
 * public Product patch(Long id, ProductRequest req) {
 *   Product existing = getOrThrow(id);
 *   mapper.updateEntity(existing, req);  // nulls in req won't overwrite
 *   return repo.save(existing);
 * }
 * }</pre>
 *
 * @apiNote Mapping behavior is compile-time generated; if you add new fields to the entity/DTOs,
 *          consider enabling MapStruct's {@code unmappedTargetPolicy = ReportingPolicy.ERROR} to catch omissions.
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Creates a new {@link Product} from an incoming request.
     *
     * <p>Intended for <em>create</em> flows; the returned entity has {@code id == null} and
     * is ready to be persisted.</p>
     *
     * @param dto validated request payload containing user-provided fields
     * @return a new {@link Product} with fields copied from {@code dto}
     */
    Product toEntity(ProductRequest dto);

    /**
     * Creates a {@link ProductResponse} DTO suitable for API responses.
     *
     * @param entity a managed {@link Product} entity (typically loaded or just persisted)
     * @return a value DTO reflecting the entity's current state
     */
    ProductResponse toResponse(Product entity);

    /**
     * Updates an existing {@link Product} in-place from a {@link ProductRequest}.
     * Fields that are {@code null} in {@code source} are <em>ignored</em> and do not overwrite
     * non-null values in {@code target}. This makes the method safe for PATCH-like flows as well
     * as PUT flows where validation guarantees non-null fields.
     *
     * @param target the entity to mutate (usually loaded from the database)
     * @param source the incoming request containing new values
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Product target, ProductRequest source);
}
