---
id: p1-ch1-intro
title: "Chapter 1"
description: "Introduction to the Backend Architecture"
---

# Chapter 1

## **Introduction to the Backend Architecture**

Let’s start with the big picture of our backend. Building a backend is like constructing the foundation and engine of a house. It’s the part of the application that clients (like frontends or mobile apps) don’t see directly, but it powers everything. We’ll be building a **RESTful API** for a product management system - imagine the backend of an online store where you can list products, search them, add new products, update or delete them.

### **What is RESTful Architecture?**

**REST (Representational State Transfer)** is a style of designing networked applications. In a RESTful design, we treat server-side data as **resources** (e.g., “products”), and we use standard HTTP methods to operate on them:

> **GET** to retrieve data (e.g., GET /products to list products).
>
> **POST** to create new data (e.g., POST /products to add a new product).
>
> **PUT** to update existing data (e.g., PUT /products/42 to replace product with ID 42).
>
> **DELETE** to remove data (e.g., DELETE /products/42 to delete product 42).

Each resource is typically accessed via a URL (endpoint). For example, /api/products might refer to the collection of product resources, and /api/products/42 to a specific product with ID 42. Clients and servers exchange data usually in **JSON** format for web APIs.

### **Why REST?** 
It’s stateless (each request contains all info needed, so server doesn’t have to remember earlier requests), scalable (can handle many clients easily), and widely used. Most libraries, tools, and developers are familiar with HTTP and REST, making it a sensible default for CRUD-heavy applications.

There are alternatives:

> **GraphQL**: a query language that allows clients to ask for exactly the data they need in one request. GraphQL is powerful for complex querying but adds complexity and a learning curve, making it perhaps overkill for straightforward create-read-update-delete (CRUD) operations.
>
> **gRPC** or other RPC frameworks: these use binary protocols and code generation for high performance and type safety, but they require more setup and are not as directly browser-friendly as REST+JSON.

For our needs (a simple product management backend), REST hits the sweet spot of simplicity and clarity.

### **High-Level Backend Architecture**

Our Spring Boot backend will follow a **layered architecture**, a common pattern in enterprise Java applications (and indeed in many other languages too). Picture it like a layered cake or a sandwich, where each layer has a distinct responsibility:

> **Controller Layer (Web Layer)** – _The top bun_: Handles incoming HTTP requests and outgoing HTTP responses. It’s the gatekeeper: it receives requests, validates and converts inputs, and calls the next layer (service) to actually do the work. After getting results, it formats and sends out responses (usually as JSON). You can think of controllers as the receptionist in an office: they greet the visitor (client request), check what’s needed (validate input), and direct the visitor to the right department.

> **Service Layer (Business Logic Layer)** – _The fillings_: Implements the core logic of the application. This is where “the work” happens. Services orchestrate tasks like creating a product, applying business rules (e.g., “the price can’t be negative”), and coordinating data flows. They often interact with databases via repositories. The service layer can also manage things like transactions (ensuring that a series of database operations either all succeed or all fail together). Continuing our analogy, if controllers are receptionists, services are managers – they know how to fulfill the request by coordinating various operations, but they don’t directly do low-level data storage themselves.

> **Repository Layer (Data Access Layer)** – _The bottom bun_: Handles interaction with the database. We use Spring Data JPA, which means our repositories are essentially an abstraction over a relational database. They offer methods to save, query, update, and delete data without us writing SQL queries manually for common operations. Repositories are like the filing clerks or librarians – they know how to store and retrieve information efficiently, but they don’t decide what to do with it beyond simple operations.

In addition to these core layers, our backend will include:

> **Entities and Domain Models**: These are the classes that represent our data (in our case, a Product class representing a product). These are typically annotated to map to database tables via JPA. You can think of them as the vocabulary or nouns of our system.

> **DTOs (Data Transfer Objects)**: These are classes that define what data is sent to or received from the API. For example, when a client creates a product, they send a JSON with name and price – that can map to a ProductRequest DTO. When we return a product, we might send back an id along with name and price – that’s a ProductResponse DTO. We use DTOs to decouple the external API model from the internal database model (for security and flexibility).

> **Mappers**: To convert between entity and DTO (and vice versa), we’ll use a mapping library (MapStruct) which auto-generates the conversion code. This saves us from writing a lot of boilerplate copying fields.
>
> **Validation and Exception Handling**: We will have validation rules (e.g., a product name can’t be blank, price must be positive) declared via annotations. A global exception handler will catch errors (like validation failures or “product not found” exceptions) and turn them into user-friendly HTTP responses (for instance, a 400 Bad Request with details of validation errors, or a 404 Not Found if a product ID doesn’t exist).

> **CORS Configuration**: Since our frontend (which we will build in Part II) likely runs on a different origin (domain/port) than our backend, we need to configure CORS so that the browser allows the frontend to call our API. This is a security measure; we’ll set it up to only allow trusted origins.

> **Testing**: Although not a “layer” per se, we will write tests for each layer (or across layers) to ensure our backend works as expected. This includes unit tests for services and integration tests for repository and controllers.

### **Why a Layered Structure?** 
It’s all about **separation of concerns** and **maintainability**. By keeping each layer focused, we make the code easier to understand and modify:

> If you change how the web API looks (say, you rename a field or add a new endpoint), you mostly deal with the Controller and maybe the DTOs, without touching the service or repository logic.
>
> If you change how data is stored (e.g., switch from an in-memory H2 database to a PostgreSQL server, or even to a NoSQL store), you might only need to adjust the Repository layer and the configuration.
>
> If you have a complex new business rule, you implement or adjust it in the Service layer, and the controllers or repositories likely remain unchanged.

This structure also aids in testing: you can test each part in isolation by mocking the others. For example, test the Service by mocking the Repository, or test the Controller by mocking the Service.

**Drawbacks?** For simple applications, all these layers can feel like overkill or boilerplate. Indeed, for a tiny app, one might combine some of these (for instance, have the controller directly use a repository). But we’re setting things up in a scalable way - our example app is simple, but the patterns we use are the same ones you’d use in a large enterprise system with many entities and complex logic. Starting with a good structure means the app can grow without a total rewrite.

### **Data Flow in Our Application**

To illustrate how these pieces interact, let’s walk through a typical request: “**Create a new product.”**

> A client (perhaps our React frontend, or a tool like Postman) sends an HTTP **POST** request to /api/products with a JSON body containing the product details (e.g., \{"name": "Coffee Mug", "price": 12.50\}).
>
> > The **Controller** (specifically our ProductController) has a method mapped to POST /api/products. Spring Boot routes the request here. The controller method takes a ProductRequest DTO as input, which Spring automatically deserializes from JSON and, thanks to validation annotations, also checks for validity (e.g., that name isn’t blank, price isn’t null and is positive).
> >
> > The controller sees if validation passed; if not, a validation exception will be thrown and handled by our global exception handler (returning a 400 Bad Request with error messages, without ever touching the service or repository).
> >
> > If input is valid, the controller uses the **mapper** to convert the ProductRequest DTO to a Product entity object.
> >
> > The controller then calls the appropriate **Service** method, e.g., productService.create(newProduct).
>
> > The **Service** (ProductService.create) receives the Product entity object. Here, we might apply business rules (for example, ensure a default value for something, or check if a product with the same name already exists - though we won’t do that in this simple app). The service method is marked @Transactional, meaning Spring will manage a database transaction here (all database actions within the method will either all succeed or all be rolled back if an error occurs).
> >
> > The service calls the **Repository**, e.g., productRepo.save(product), to save the product to the database.
> >
> > The repository returns the saved entity (which now has an id generated by the database).
> >
> > The service might do other things if needed (in more complex apps, maybe call other services, send a notification, etc.), but in our case it just returns the saved entity.
>
> > Back in the **Controller**, we now have a saved Product entity. The controller uses the **mapper** again to convert this into a ProductResponse DTO (which might just be identical fields in this simple case). The controller then builds an HTTP response:
> >
> > Usually for a creation, it’s good practice to return a 201 Created status. We also include a Location header pointing to the new resource (/api/products/\{id\} for the new product’s ID).
> >
> > The response body will contain the JSON representation of ProductResponse (including the new id, name, price).
>
> The client receives this HTTP response with status 201 and the JSON data. It now knows the operation succeeded and has the new product’s ID in case it needs it.

Another example: “**Get product by ID.”** A GET request to /api/products/42 flows similarly: controller calls service, service calls repository’s findById(42). If found, great; if not, the repository returns empty and the service throws a ResourceNotFoundException, which our global exception handler catches and translates to a 404 Not Found with a message. The controller then returns the product data (converted to DTO) with a 200 OK if found.

By structuring the app this way, each layer is fairly simple:

> Controllers don’t contain business logic or data access code; they just handle I/O (HTTP in, HTTP out).
>
> Services contain logic but no HTTP or JSON handling, and minimal knowledge of database details.
>
> Repositories have database access code but no knowledge of why or how the data is used above.

This separation is not unique to Java/Spring-many frameworks follow similar patterns. For example, in .NET you often have Controllers, Services, and Repositories (and they even have similar validation and dependency injection concepts). In Node.js, you can structure an app with routers/controllers, services, and data access (especially if using an ORM or a framework like NestJS which explicitly encourages this structure). In fact, a Stack Overflow question about applying Spring’s layered architecture in Node got answers explaining that it’s absolutely possible and beneficial to separate controllers, services, etc., even if Node doesn’t force you to. Most mature frameworks in any language aim to separate concerns in comparable ways, even if terminology differs (e.g., MVC architecture in Ruby on Rails or Django also splits responsibilities across Model, View, Controller layers).

Now that we have the conceptual overview, let’s get our hands dirty. Before coding, you would typically set up a Spring Boot project. You can use the Spring Initializr (start.spring.io) to generate a project with the dependencies we need:

> Spring Web (for building REST APIs),
>
> Spring Data JPA (for database access),
>
> Spring Validation (included in web for @Valid, etc.),
>
> H2 Database (for an easy in-memory dev database, or you could use PostgreSQL if you prefer),
>
> Lombok (to reduce boilerplate for getters/setters),
>
> MapStruct (we’ll add this for mapping, as a compile-time code generator).

Make sure you have Java 17+ (Spring Boot 3 requires Java 17 or higher). An IDE like IntelliJ IDEA or Eclipse can help by automatically compiling and running the app. We’ll assume usage of Maven or Gradle to manage dependencies. Once you have the skeleton project, we can start defining our domain model.
