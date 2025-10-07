/**
 * Data Transfer Objects (DTOs) for the Product API.
 *
 * <p>Why use DTOs instead of exposing entities directly?</p>
 * <ul>
 *   <li>
 *       <b>Separation of concerns:</b> Entities map to database tables,
 *       while DTOs define the shape of data exposed in the API.
 *   </li>
 *
 *   <li>
 *       <b>Security:</b> Entities may contain internal fields (e.g., audit
 *       data, relationships) that should not be visible to clients.
 *   </li>
 *
 *   <li>
 *       <b>Flexibility:</b> DTOs allow us to evolve the API independently
 *       of the database schema. For example, we can rename fields or
 *       combine data from multiple entities.
 *   </li>
 *
 *   <li>
 *       <b>Validation:</b> Request DTOs (like {@link com.example.springrest.dto.ProductRequest})
 *       carry validation rules to ensure only valid data reaches the service layer.
 *   </li>
 *
 *   <li>
 *       <b>Clarity in API docs:</b> Response DTOs (like {@link com.example.springrest.dto.ProductResponse})
 *       produce clean Swagger/OpenAPI documentation with examples.
 *   </li>
 * </ul>
 *
 * <p>In short: <b>Entities are for persistence; DTOs are for communication.</b></p>
 */
package com.example.springrest.dto;
