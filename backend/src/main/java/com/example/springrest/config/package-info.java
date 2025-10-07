/**
 * Application-wide configuration classes.
 *
 * <p>This package contains Spring configuration classes that customize the behavior
 * of the application context and web layer.</p>
 *
 * <h2>Typical responsibilities</h2>
 * <ul>
 *   <li>Defining {@code @Bean} methods for infrastructural components.</li>
 *   <li>Customizing Spring MVC via {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}.</li>
 *   <li>Centralizing cross-cutting settings (e.g., CORS, OpenAPI, security policies).</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <ul>
 *   <li>{@link com.example.springrest.config.CorsConfig} — configures CORS access for REST endpoints.</li>
 * </ul>
 */
package com.example.springrest.config;
