package com.example.springrest.controllers;

import com.example.springrest.dto.ProductRequest;
import com.example.springrest.dto.ProductResponse;
import com.example.springrest.mappers.ProductMapper;
import com.example.springrest.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;


/**
 * REST controller for managing products.
 *
 * <p><strong>Responsibilities</strong>:
 * <ul>
 *   <li>List and search products with pagination &amp; sorting.</li>
 *   <li>Create, update, and delete products.</li>
 *   <li>Return 404 for missing resources (via the service’s {@code getOrThrow}).</li>
 * </ul>
 *
 * <p><strong>Notes</strong>:</p>
 * <ul>
 *   <li>
 *     Parameter-level validation annotations (e.g., {@code @Min(1)} on {@code id})
 *     are included for clarity, but they will not be enforced until we introduce
 *     {@code @Validated} in <em>Part III</em> of the book.
 *   </li>
 *   <li>
 *     For now, invalid IDs (e.g., {@code 0} or negative numbers) will flow through
 *     to the service layer and typically result in a {@code 404 Not Found} via
 *     {@link com.example.springrest.services.ProductService#getOrThrow(Long)}.
 *   </li>
 *   <li>
 *     This keeps the early chapters simpler, while still hinting at stronger
 *     validation to come.
 *   </li>
 * </ul>
 *
 * <p><strong>Examples</strong>:</p>
 * <pre>{@code
 * # List (page 0, size 20, sort by id DESC)
 * curl 'http://localhost:8080/api/products?page=0&size=20&sort=id,desc'
 *
 * # Search by name (case-insensitive)
 * curl 'http://localhost:8080/api/products/search?q=mug&page=0&size=10'
 *
 * # Read one
 * curl 'http://localhost:8080/api/products/42'
 *
 * # Create
 * curl -X POST 'http://localhost:8080/api/products' \
 *      -H 'Content-Type: application/json' \
 *      -d '{"name":"Coffee Mug","price":12.99}'
 *
 * # Update (PUT is idempotent here)
 * curl -X PUT 'http://localhost:8080/api/products/42' \
 *      -H 'Content-Type: application/json' \
 *      -d '{"name":"Travel Mug","price":14.50}'
 *
 * # Delete
 * curl -X DELETE 'http://localhost:8080/api/products/42'
 * }</pre>
 *
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/products", produces = "application/json")
@RequiredArgsConstructor
//@Validated
@Tag(name = "Products", description = "Operations on the product catalog")
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    /**
     * Returns a paginated list of products.
     *
     * <p>Default page size is 20, sorted by {@code id DESC}. Override with
     * query params like {@code ?page=1&size=50&sort=name,asc}.</p>
     *
     * @param pageable pagination & sorting (page, size, sort)
     * @return page of {@link ProductResponse}
     */
    @Operation(
            summary = "List products",
            description = "Returns a paginated, sortable list of products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of products returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.springframework.data.domain.Page.class),
                            examples = @ExampleObject(
                                    name = "ProductsPage",
                                    value = """
                    {
                      "content": [
                        {"id":1,"name":"Coffee Mug","price":12.99},
                        {"id":2,"name":"Tea Cup","price":7.50}
                      ],
                      "pageable": {"pageNumber":0,"pageSize":20},
                      "totalElements": 2,
                      "totalPages": 1,
                      "last": true,
                      "size": 20,
                      "number": 0,
                      "sort": {"empty": false, "sorted": true, "unsorted": false},
                      "first": true,
                      "numberOfElements": 2,
                      "empty": false
                    }
                    """
                            )
                    )
            )
    })
    @GetMapping
    public Page<ProductResponse> getAll(
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.list(pageable).map(mapper::toResponse);
    }

    /**
     * Searches products by (case-insensitive) name substring.
     *
     * @param q        required query string to match within product names
     * @param pageable pagination & sorting (page, size, sort)
     * @return page of matches
     */
    @Operation(
            summary = "Search products by name",
            description = "Case-insensitive contains search on product names."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page of matches returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = org.springframework.data.domain.Page.class)
                    )
            )
    })
    @GetMapping("/search")
    public Page<ProductResponse> search(
            @Parameter(
                    name = "q",
                    description = "Case-insensitive substring to match within product names",
                    required = true,
                    examples = {
                            @ExampleObject(name = "Mug", value = "mug"),
                            @ExampleObject(name = "Coffee", value = "coffee")
                    }
            )
            @RequestParam("q") String q,
            @ParameterObject
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.searchByName(q, pageable).map(mapper::toResponse);
    }

    /**
     * Reads a product by id.
     *
     * @param id product id (≥ 1)
     * @return the product
     */
    @Operation(
            summary = "Get product by id",
            description = "Returns a single product by its identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @Parameter(description = "Product id (>= 1)", example = "42")
            @PathVariable @Min(value = 1, message = "ID must be >= 1") Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getOrThrow(id)));
    }

    /**
     * Creates a new product.
     *
     * <p>On success returns <strong>201 Created</strong> with a {@code Location}
     * header pointing to {@code /api/products/{id}}.</p>
     *
     * @param request validated product payload
     * @return created product (body) and Location header
     */
    @Operation(
            summary = "Create product",
            description = "Creates a new product and returns it with a Location header."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "New product details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "name": "Coffee Mug",
                      "price": 12.99
                    }
                    """)
                    )
            )
            @RequestBody @Valid ProductRequest request) {
        var saved = service.create(mapper.toEntity(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(saved));
    }

    /**
     * Replaces a product’s name and price.
     *
     * <p>Semantically a full update (PUT). If you later add PATCH, you can reuse
     * the mapper’s partial update semantics.</p>
     *
     * @param id      product id (≥ 1)
     * @param request validated payload
     * @return updated product
     */
    @Operation(
            summary = "Update product",
            description = "Replaces a product’s fields by id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ProductResponse> update(
            @Parameter(description = "Product id (>= 1)", example = "42")
            @PathVariable @Min(value = 1, message = "ID must be >= 1") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Updated product details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequest.class)
                    )
            )
            @RequestBody @Valid ProductRequest request) {
        var updated = service.update(id, entity ->
                mapper.updateEntity(entity, request)
        );
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    /**
     * Deletes a product by id.
     *
     * @param id product id (≥ 1)
     * @return 204 No Content on success
     */
    @Operation(
            summary = "Delete product",
            description = "Deletes a product by id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Product id (>= 1)", example = "42")
            @PathVariable @Min(value = 1, message = "ID must be >= 1") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
