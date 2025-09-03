# Part I – Building the Backend (Spring Boot)

Welcome to Part I of the Full Stack Engineering Book.  
In this section, we build the backend foundation: a RESTful API with Spring Boot that manages products. Think of this as the “engine room” of our application — the part that powers everything the frontend will later display.

## 📚 Chapters in Part I
1. Introduction to the Backend Architecture  
2. Defining the Domain Model with JPA  
3. Creating API Models (DTOs)  
4. Mapping Between Layers with MapStruct  
5. Implementing the Repository Layer  
6. Building the Service Layer for Business Logic  
7. Exposing Endpoints in the Controller Layer  
8. Handling Requests and Responses  
9. Global Exception Handling  
10. Configuring CORS for Cross-Origin Requests  
11. Persistence and Database Migrations  
12. Testing Strategies  
13. Common Pitfalls and How to Avoid Them  
14. Extending the Application  
15. Conclusion and Next Steps  

## ⚙️ Tech Stack for Part I
- Backend Framework: Spring Boot 3.5.x  
- Language: Java 17+  
- Database: H2 (in-file) for dev/testing  
- ORM: Spring Data JPA  
- Validation: Jakarta Bean Validation  
- Boilerplate Reduction: Lombok  
- DTO Mapping: MapStruct

## 🚀 Getting Started

Clone this repository and navigate to the Part I folder:

```bash
git clone https://github.com/brianronock/fullstackdev_spring-and-react.git
cd fullstackdev_spring-and-react/Part1_RESTful-API-SpringBoot
```

Build and run the app:

```bash
./mvnw spring-boot:run
```

The backend will start on http://localhost:8080.

## 🔗 API Endpoints (Chapter 3+)
- `GET /api/products` → List all products  
- `GET /api/products/{id}` → Get product by ID  
- `POST /api/products` → Create a new product  
- `PUT /api/products/{id}` → Update a product  
- `DELETE /api/products/{id}` → Delete a product  

All requests/response bodies use JSON.

## 🧪 Testing

You can test the API with Postman or cURL:

```bash
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{"name": "Coffee Mug", "price": 12.50}'
```

## 🔮 What’s Next?

In Part II, we will build the frontend with React, connect it to this backend, and create a full-stack application.  

After that:
- **Part III – Advanced Backend Features**: Authentication, authorization, caching, search, observability, etc.  
- **Part IV – Advanced Frontend Features**: Authentication UI, state management with Redux Toolkit, advanced routing, optimistic updates.  
- **Part V – Infrastructure & Deployment**: CI/CD, Docker, cloud deployment, logging, monitoring, scaling, security.  

Part I lays the foundation; the following parts expand it into a complete, production-ready system.
