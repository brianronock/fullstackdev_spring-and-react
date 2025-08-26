/**
 * Application root package for the Spring Boot backend.
 *
 * <p>This package hosts the {@code @SpringBootApplication} entry point and
 * forms the <em>component-scan root</em> for the project. Keeping the main
 * application class here ensures that Spring discovers beans in all
 * subpackages automatically.</p>
 *
 * <h2>Package layout</h2>
 * <ul>
 *   <li>{@link com.example.springrest.models models} – JPA entities (domain/persistence layer).</li>
 *   <li>{@link com.example.springrest.dto dto} – Request/response Data Transfer Objects.</li>
 *   <li>{@link com.example.springrest.mappers mappers} – MapStruct mappers (entity ↔ DTO).</li>
 *   <li>{@link com.example.springrest.repositories repositories} – Spring Data JPA repositories.</li>
 *   <li>{@link com.example.springrest.services services} – Application/business logic.</li>
 *   <li>{@link com.example.springrest.controllers controllers} – Web layer (REST endpoints).</li>
 *   <li>{@link com.example.springrest.exceptions exceptions} – Custom exceptions &amp; (optionally) handlers.</li>
 *   <li>{ config} – Application/Web configuration (e.g., CORS, OpenAPI).</li>
 * </ul>
 *
 * <h2>Conventions</h2>
 * <ul>
 *   <li><b>Component scanning:</b> Place {@code SpringRestApplication} in this package so
 *       {@code @ComponentScan} (implied by {@code @SpringBootApplication}) picks up all subpackages.</li>
 *   <li><b>Persistence:</b> If entities or repositories are moved outside this base package,
 *       configure {@code @EntityScan} and {@code @EnableJpaRepositories} explicitly.</li>
 *   <li><b>API surface:</b> Expose DTOs from {@code dto} in controllers; keep entities internal.</li>
 *   <li><b>Transactions:</b> Apply {@code @Transactional} on service methods that write.</li>
 * </ul>
 *
 * <h2>Typical entry point</h2>
 * <pre>{@code
 * package com.example.springrest;
 *
 * import org.springframework.boot.SpringApplication;
 * import org.springframework.boot.autoconfigure.SpringBootApplication;
 *
 * @SpringBootApplication
 * public class SpringRestApplication {
 *     public static void main(String[] args) {
 *         SpringApplication.run(SpringRestApplication.class, args);
 *     }
 * }
 * }</pre>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li><b>Profiles:</b> Use {@code application-<profile>.yaml} for environment overrides.</li>
 *   <li><b>Docs:</b> Javadoc is published with the site; OpenAPI/REST Docs can be added later.</li>
 * </ul>
 */
package com.example.springrest;
