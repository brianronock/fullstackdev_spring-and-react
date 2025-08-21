---
id: p1-ch3-dto-models
title: "Chapter 3"
description: "Creating API Models (DTOs)"
---
# Chapter 3

## **Creating API Models (DTOs)**

In a well-designed application, the objects that represent data in the database (entities) are often different from the objects that you expose to the outside world via the API. The reason is that your internal representation might have extra fields or links (like relationships, audit fields, etc.) that the API doesn<span dir="rtl">'</span>t need to expose, or that might even be sensitive. Also, by decoupling the two, you can evolve your internal model without breaking external contracts, or vice versa.

These external-facing objects are usually called **DTOs (Data Transfer Objects)** or sometimes *API models*. They are simple containers for data with typically no business logic.

### Why Use DTOs?

Couldn<span dir="rtl">'</span>t we just use our Product entity class directly in the controllers and have Spring Boot automatically convert it to JSON and vice versa? Yes, we could annotate Product with Jackson annotations to, say, ignore certain fields. However, using the entity directly in the web layer has drawbacks:

> **Security**: Suppose our Product entity had an ownerId field or some internal status. If we return the entity directly, we might accidentally serialize sensitive info. With DTOs, we explicitly choose what fields to include.

> **Flexibility**: You might want the API to use different naming or combine fields. For example, maybe internally you have firstName and lastName, but you want to expose fullName in the API. A DTO can do that transformation.

> **Decoupling**: If later you change the entity (like split one class into two, or rename a field), having a DTO layer means your API doesn<span dir="rtl">'</span>t necessarily have to change at the same time. They<span dir="rtl">'</span>re loosely coupled.

> **Validation**: It often makes sense to validate at the boundary (the API layer). By using a DTO for input, you can put validation annotations on it (e.g., <span dir="rtl">"</span>name must be not blank, price minimum 0.01") and Spring will automatically validate incoming requests. This catches errors before even reaching deeper layers.

For these reasons, in larger applications you almost always see separate DTO classes. In small apps, some might skip it to save time, but since we<span dir="rtl">'</span>re aiming to show best practices, we<span dir="rtl">'</span>ll include them.

### Designing the DTOs

We have two main DTOs:

> **ProductRequest:** what the client sends when creating or updating a product. This will include the product<span dir="rtl">'</span>s name and price, since those are the fields a client can set. We will put validation constraints here to ensure, for example, that the name isn<span dir="rtl">'</span>t empty and the price is positive. We won<span dir="rtl">'</span>t include an ID here because the client doesn<span dir="rtl">'</span>t set the ID (the server generates it on create, and on update the ID is in the URL path, not in the body).

> **ProductResponse:** what we send back to the client when they ask for product data (either one product or a list of products). This will include the product<span dir="rtl">'</span>s id, name, and price. We typically don<span dir="rtl">'</span>t need validation on responses (we assume our data is valid by construction).

We<span dir="rtl">'</span>ll also use Java<span dir="rtl">'</span>s newer syntax for simple data carriers: **records** (available since Java 14 and finalized in Java 16). A record is a concise way to create an immutable data class with final fields, equals/hashCode, etc., auto-generated. It<span dir="rtl">'</span>s perfect for DTOs which are just data holders. If you<span dir="rtl">'</span>re not familiar with records, you can think of them as a shorthand for a class with private final fields, a constructor, and getters.

Alternatively, we could use regular classes with Lombok for brevity, but let<span dir="rtl">'</span>s use records for variety. One caveat: Spring<span dir="rtl">'</span>s validation (JSR 380) doesn<span dir="rtl">'</span>t directly support applying method-level constraints on record components, but it *does* support them on the constructor parameters of the record. In simpler terms, you can annotate the record components and Spring will validate those when binding.

Let<span dir="rtl">'</span>s write the code for our DTOs:

### **ProductRequest.java**

```java
package com.example.springrest.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank(message = "Name is mandatory")
    @Size(max = 120, message = "Name must be at most 120 characters")
    String name,
    
    @NotNull(message = "Price must be provided")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    BigDecimal price
) {}
```

### **ProductResponse.java**

```java
package com.example.springrest.dto;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    BigDecimal price
) {}
```

### Breakdown of DTO fields and validations

For **ProductRequest**:

> We annotate name with @NotBlank (ensures the string is not null and not just whitespace) and @Size(max = 120) (ensures the length is at most 120 chars). This mirrors the database constraint. We could also put a minimum length if desired, but not blank covers the non-empty case.

> We annotate price with a few:
>
>> @NotNull – price must be provided.
>
>> @DecimalMin("0.01") – the smallest price allowed is 0.01 (assuming a product cannot be free or zero-priced; if we wanted to allow free items, we could use <span dir="rtl">"</span>0.0" as min and perhaps allow zero, but let<span dir="rtl">'</span>s say at least one cent).
>
>> @Digits(integer = 10, fraction = 2) – this ensures that the numeric value has at most 10 digits in the integer part and at most 2 in the fractional part. This corresponds to the precision we set in the entity (12,2). Why 10 integer digits instead of 12? Because 12 total minus 2 fraction = 10 integer digits. This basically ensures that if someone tries to send a price like 12345678901.00 (which is 11 digits in the integer part), validation will fail rather than potentially causing a database error. It<span dir="rtl">'</span>s a good practice to have the DTO validation align with what the database can handle.
>
> These validations will automatically trigger if a client<span dir="rtl">'</span>s JSON is missing the price, or has a blank name, or an overly long name, etc., resulting in a 400 Bad Request with error details that our global handler will format later.

For **ProductResponse**:

> We include id, name, and price with their types. We do not put validations here because this is for data we *output*. We trust our system to produce valid data. (Also, Spring doesn<span dir="rtl">'</span>t validate responses by default, only requests, which makes sense.)
>
> We might consider formatting issues (like we might want to format price as a string with a currency symbol in some presentation layer, but since this is a low-level API, we<span dir="rtl">'</span>ll keep it as numeric and let clients format if needed).
>
> The id is a Long and can be null in cases where, say, something went wrong (but normally, if we<span dir="rtl">'</span>re returning a ProductResponse, it should have an id). We could make it a primitive long if we want to ensure it<span dir="rtl">'</span>s always present. Using Long (wrapper) allows null, but in practice, we will always set it.

### Why not just use Product entity for responses?
As discussed, one reason is future changes. For instance, imagine later we add a field lastUpdated to Product for internal use. We might not want to always expose that. By using a ProductResponse, we can choose not to include it in the API. It also decouples our API from our database: we could even restructure our database (say, split Product into multiple tables) and as long as we still produce the same ProductResponse JSON to clients, they don<span dir="rtl">'</span>t need to know about the internal change.

### Alternative approaches
In some projects, people use libraries like **MapStruct** (which we will) or **ModelMapper** or manually write converters to go between entity and DTO. Another approach is to use something like Spring Projection or interface-based projections from JPA to directly get the data you need for a response, but that<span dir="rtl">'</span>s a more advanced use-case and can blur the separation. We<span dir="rtl">'</span>ll stick to explicit mapping.

### A note on immutability
By using records (or if not using Java 16+, one might use Lombok<span dir="rtl">'</span>s @Value for immutable classes), we make our DTOs immutable. This is generally good because we treat them as values. You create it and use it; you don<span dir="rtl">'</span>t need to modify it after creation. This prevents accidental side effects (e.g., some code changing a name in a DTO thinking it<span dir="rtl">'</span>s okay, but that doesn<span dir="rtl">'</span>t affect the entity or DB - it would only confuse logic). Immutability in multi-threaded environments (like a web server) is also beneficial for safety. The entity, on the other hand, is a mutable JPA entity (you load it, set fields, etc., then save). That<span dir="rtl">'</span>s fine because JPA needs to track changes, but at the boundaries (request/response) we prefer simpler immutable structures.

Now that we have our DTOs, we need to handle converting between Product and these DTOs. We could write code in our controller or service to do new Product(dto.getName(), dto.getPrice()) and vice versa. But that gets tedious as the number of fields grows. Instead, we<span dir="rtl">'</span>ll use **MapStruct** to automatically generate these mappings for us.
