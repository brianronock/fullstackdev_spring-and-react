package com.example.springrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p><strong>Spring integration</strong>:
 * Annotated with {@link ResponseStatus @ResponseStatus(HttpStatus.NOT_FOUND)},
 * so controllers do not need to catch it explicitly. When thrown during a request,
 * the HTTP response code will automatically be set to <code>404 NOT FOUND</code>
 * with the exception message as the error detail.</p>
 *
 * <h2>Typical usage</h2>
 * <pre>{@code
 * // In a service method
 * public Product getOrThrow(Long id) {
 *     return repo.findById(id)
 *         .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
 * }
 *
 * // In a controller, no try/catch needed:
 * @GetMapping("/{id}")
 * public ProductResponse one(@PathVariable Long id) {
 *     Product product = productService.getOrThrow(id);
 *     return mapper.toResponse(product);
 * }
 * }</pre>
 *
 * <p><strong>API design note</strong>: a dedicated exception class provides
 * semantic clarity, and keeps controller code concise.</p>
 *
 * @since 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message a descriptive message explaining the cause of the exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
