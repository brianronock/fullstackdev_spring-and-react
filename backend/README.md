# Part III – Advanced Backend Features (Spring Boot)
Welcome to Part III of the Full Stack Engineering Book.
Here we evolve our backend from a simple CRUD API into a production-ready service — secure, robust, observable, and scalable. If Part I was the “engine room,” this part is the control center: it’s where we add all the critical systems that real-world backends need to serve users reliably.

---

## Chapters in Part III

1. Authentication & Authorization (Spring Security + JWT)
    - Secure endpoints with stateless JWT tokens
    - Define roles (ROLE_ADMIN, ROLE_USER) and enforce permissions
    - Build a `/api/auth/login` endpoint
    - Parse and validate JWTs with a custom filter

2. Robust Validation & Global Error Handling
    - Use ProblemDetail (RFC 7807) for consistent error responses
    - Centralize exception handling with `@RestControllerAdvice`
    - Support validation groups for create vs. update

3. Concurrency: Optimistic Locking & Transactions
    - Prevent lost updates with `@Version`
    - Handle concurrent edits gracefully with HTTP 409 responses

4. Advanced Searching & Filtering
    - Build flexible queries with Spring Data JPA Specification
    - Support full-text search and numeric filters
    - Combine with pagination and sorting for scalable queries

5. Partial Updates with HTTP PATCH (JSON Merge Patch)
    - Update only what changed — without sending the full resource
    - Apply and validate JSON merge patches
    - Understand null vs omitted fields

6. Caching with Redis
    - Speed up reads with Spring Cache abstraction
    - Use `@Cacheable`, `@CacheEvict`, and `@CachePut`
    - Invalidate caches safely after writes

7. Auditing & Soft Deletes
    - Track `createdAt`, `updatedAt`, `createdBy`, and `updatedBy` automatically
    - Enable soft deletes with `@SQLDelete` and `@Where`

8. File Uploads (Images)
    - Add endpoints for product image upload and retrieval
    - Store images on disk (or cloud) and serve them as resources

9. Observability: Actuator, Metrics, and Tracing
    - Expose health and metrics endpoints
    - Create custom Prometheus metrics
    - Implement correlation IDs for end-to-end request tracing

10. Rate Limiting & Throttling (Bucket4j)
    - Prevent abuse with token-bucket rate limiting
    - Return `429 Too Many Requests` when limits are exceeded

11. API Versioning & OpenAPI Documentation
    - Version APIs (`/api/v1/...`) to support evolution
    - Generate Swagger/OpenAPI documentation with springdoc-openapi

12. Database Migrations & Testing
    - Manage schema changes with Flyway
    - Run integration tests with PostgreSQL Testcontainers

13. Asynchronous Work & Domain Events
    - Publish domain events and handle them asynchronously
    - Decouple slow background work from user requests

14. Hardening & Best Practices
    - Checklist for input validation, security, resilience, and observability
    - Production-ready tips for scaling and operating the service

---

## Tech Stack for Part III

| Layer             | Technology                                     |
|-------------------|------------------------------------------------|
| Backend Framework | Spring Boot 3.5.x                              |
| Language          | Java 17+                                       |
| Security          | Spring Security + JWT                          |
| Database          | PostgreSQL (with Flyway migrations)            |
| ORM               | Spring Data JPA                                |
| Validation        | Jakarta Bean Validation                        |
| Caching           | Redis                                          |
| Metrics & Tracing | Spring Boot Actuator + Micrometer (Prometheus) |
| Rate Limiting     | Bucket4j                                       |
| Testing           | JUnit 5 + Testcontainers                       |
| Documentation     | Springdoc OpenAPI / Swagger UI                 |

---

## Getting Started

Clone the project and navigate to the Part III backend folder:

```bash
git clone https://github.com/brianronock/fullstackdev_spring-and-react.git
cd fullstackdev_spring-and-react/backend
```

Build and run the app:

```bash
./gradlew bootRun
```

The backend will start on http://localhost:8080.

In later chapters, you'll also need:
- Redis running locally (default: localhost:6379)
- PostgreSQL or Docker for database (Flyway migrations will run on startup)

## Authentication Flow
1. Login
```http request
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123"
}
```
Response:
```http
{ "token": "<your-jwt-token>" }
```

2. Use the token in subsequent requests:
```http request
GET /api/v1/products
Authorization: Bearer <your-jwt-token>
```

## Enhanced API Endpoints

| Method | Endpoint                    | Description                              | Roles       |
|--------|-----------------------------|------------------------------------------|-------------|
| GET    | /api/v1/products            | List products (with filters, pagination) | USER, ADMIN |
| GET    | /api/v1/products/{id}       | Get product by ID (cached)               | USER, ADMIN |
| POST   | /api/v1/products            | Create a new product                     | ADMIN       |
| PUT    | /api/v1/products/{id}       | Update a product (with version check)    | ADMIN       |
| PATCH  | /api/v1/products/{id}       | Partial update (JSON Merge Patch)        | ADMIN       |
| DELETE | /api/v1/products/{id}       | Soft delete a product                    | ADMIN       |
| POST   | /api/v1/products/{id}/image | Upload product image                     | ADMIN       | 
| GET    | /api/v1/products/{id}/image | Retrieve product image                   | USER, ADMIN |

## Observability Endpoints
| Endpoint             | Purpose                    |
|----------------------|----------------------------|
| /actuator/health     | Health check               | 
| /actuator/metrics    | All metrics                |
| /actuator/prometheus | Prometheus scrape endpoint | 

## Testing with Testcontainers
Run integration tests with an ephemeral PostgreSQL instance:
```bash
./gradlew test
```
This will:
- Spin up a PostgreSQL container
- Run migrations automatically
- Execute full-stack integration tests

## What’s Next?

In Part IV, we will add the Advanced Frontend Features, connect it to this backend, and create a production-grade full-stack application.  

After that:
- **Part V – Infrastructure & Deployment**: CI/CD, Docker, cloud deployment, logging, monitoring, scaling, security.  
