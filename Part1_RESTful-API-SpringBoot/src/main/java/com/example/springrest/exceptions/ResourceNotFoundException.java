package com.example.springrest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource cannot be found.
 * <p>
 *     This exception is annotated with {@link ResponseStatus}, which ensures
 *     that when it is thrown from within a Spring MVC controller, the HTTP
 *     response status will automatically be set to {@code 404 NOT FOUND}
 * </p>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     * @param message a descriptive message explaining the cause of the exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
