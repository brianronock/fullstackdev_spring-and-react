package com.example.springrest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS).
 *
 * <p>
 *     This class defines global CORS rules for the application’s REST API endpoints.
 *     It ensures that frontends (such as a React or Vue SPA) hosted on different domains
 *     can interact with the backend securely.
 * </p>
 *
 * <h2>Configuration</h2>
 * <ul>
 *   <li><b>Origins:</b> Injected from the {@code app.cors.origin} property
 *   (comma-separated list, e.g. {@code http://localhost:3000,http://example.com}).</li>
 *
 *   <li><b>Paths:</b> Applies to all endpoints under {@code /api/**}.</li>
 *
 *   <li><b>Methods:</b> Allows {@code GET}, {@code POST}, {@code PUT}, {@code DELETE}, and {@code OPTIONS}.</li>
 *
 *   <li><b>Headers:</b> All request headers are allowed.</li>
 *
 *   <li><b>Credentials:</b> Explicitly disabled
 *   ({@code allowCredentials(false)}) for security unless otherwise required.</li>
 * </ul>
 *
 * <h2>Usage</h2>
 * <pre>{@code
 * # application.properties
 * app.cors.origin=http://localhost:3000,http://myfrontend.com
 * }</pre>
 *
 * With the above, JavaScript clients from either origin can access {@code /api/**}.
 *
 * @see org.springframework.web.servlet.config.annotation.CorsRegistry
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
@Configuration
public class CorsConfig {


    /**
     * Comma-separated list of allowed origins.
     * <p>
     * Example:
     * <pre>{@code
     * app.cors.origin=http://localhost:5173,http://myfrontend.com
     * }</pre>
     */
    @Value("${app.cors.origin}")
    private String allowedOrigins;

    /**
     * Defines the {@link WebMvcConfigurer} bean that applies global CORS mappings.
     *
     * @return a {@link WebMvcConfigurer} instance with the configured CORS rules
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false);
            }
        };
    }
}
