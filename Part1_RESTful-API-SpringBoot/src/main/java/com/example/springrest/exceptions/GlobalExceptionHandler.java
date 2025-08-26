package com.example.springrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for REST controllers.
 *
 * <p>
 *     This class centralizes exception handling across the entire application.
 *     Annotated with {@link RestControllerAdvice}, it automatically intercepts
 *     exceptions thrown from {@code @RestController} methods and converts them
 *     into consistent, structured HTTP responses.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Map exceptions to appropriate HTTP status codes.</li>
 *     <li>Produce JSON error payloads instead of raw stack traces.</li>
 *     <li>Improve API usability by returning descriptive, client-friendly errors.</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *     <li>
 *         Uses functional style with {@link java.util.stream.Stream} and factory
 *         methods like {@link Map#of(Object, Object)} for conciseness and immutability.
 *     </li>
 *     <li>
 *         Follows a contract of returning {@code Map<String,String>} responses,
 *         which are automatically serialized to JSON by Spring Boot.
 *     </li>
 * </ul>
 *
 * <p>Example error response (404):</p>
 * <pre>{@code
 * {
 *   "error": "Product not found with ID: 42"
 * }
 * }</pre>
 *
 * <p>Example error response (400):</p>
 * <pre>{@code
 * {
 *   "name": "Name is mandatory",
 *   "price": "Price must be greater than 0"
 * }
 * }</pre>
 *
 * @see ResourceNotFoundException
 * @see MethodArgumentNotValidException
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException}.
     *
     * <p>
     *     Returns an HTTP 404 (Not Found) response with a simple JSON object containing
     *     the error message.
     * </p>
     *
     * <h3>Old way (imperative)</h3>
     * <pre>{@code
     * Map<String, String> error = new HashMap<>();
     * error.put("error", ex.getMessage());
     * return error;
     * }</pre>
     *
     * @param ex the exception containing the cause of the resource lookup failure
     * @return a map with a single entry {@code "error": message}
     *
     * <h2>Example</h2>
     * <pre>{@code
     * {
     *   "error": "Product not found with ID: 99"
     * }
     * }</pre>
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Handles {@link MethodArgumentNotValidException} thrown when validation of
     * request bodies fails (e.g., {@code @Valid} DTOs).
     *
     * <p>
     *     Builds a map where keys are the invalid field names and values are
     *     their corresponding validation error messages.
     * </p>
     *
     * <h3>Old way (imperative)</h3>
     * <pre>{@code
     * Map<String, String> error = new HashMap<>();
     * for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
     *     error.put(fieldError.getField(), fieldError.getDefaultMessage());
     * }
     * return error;
     * }</pre>
     *
     * @param ex the exception containing details of all validation errors
     * @return a map of field names to validation messages
     *
     * <h2>Example</h2>
     * <pre>{@code
     * {
     *   "name": "Name is mandatory",
     *   "price": "Price must be greater than 0"
     * }
     * }</pre>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Validation error",
                        (msg1, msg2) -> msg1
                ));
    }
}
