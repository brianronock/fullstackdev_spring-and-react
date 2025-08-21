# Chapter 4

## **Mapping Between Layers with MapStruct**

Mapping between your internal model (entities) and external model (DTOs) is a common task. If you have just a couple of fields, writing conversion code is no big deal. But real-world entities can have dozens of fields, and writing boilerplate like dto.setX(entity.getX()); dto.setY(entity.getY()); or vice versa can become a headache - it's repetitive and easy to make mistakes (maybe you forget to map one field, or swap two fields by accident).

**MapStruct** is a code generation library that creates mapper implementations at compile time. You define an interface with mapping methods, and MapStruct will generate a class that implements that interface, mapping fields of the same name automatically and allowing customizations if needed.

### Why MapStruct over other options?

> **Manual mapping**: you write the code yourself. It's straightforward for small classes but doesn't scale well. It's also not very interesting code to write or maintain.
>
> **Reflection-based mappers** (like ModelMapper or Dozer): these work at runtime and can be flexible (dynamically figuring out how to map), but they carry a runtime performance cost and potentially less type safety (if you misconfigure, you find out at runtime).
>
> **MapStruct** generates code at compile time (so it's type-safe and fast, no reflection at runtime).
>
> Another alternative is using frameworks like **Lombok's** @Delegate or other tricks, but MapStruct is widely adopted and integrates well with Spring.

We will create a mapper interface for Product. It will have methods to:

> Convert ProductRequest (DTO) to Product (entity).
>
> Convert Product (entity) to ProductResponse (DTO).
>
> Possibly update an existing Product entity from a ProductRequest (for partial updates).

MapStruct can handle many types automatically, especially when source and target have the same field names and compatible types. Here, ProductRequest.name is a String and Product.name is a String - that maps fine. ProductRequest.price is BigDecimal, Product.price is BigDecimal - that maps fine. So for creating a new Product from ProductRequest, it's straightforward.

For mapping Product to ProductResponse, id (Long to Long), name (String to String), price (BigDecimal to BigDecimal) - all fine.

MapStruct also supports mapping update methods, where you want to copy values from a source to an existing target. We can use that for implementing an update: instead of writing code to set each field if not null, we can have MapStruct generate a method that copies non-null properties from a DTO into an existing entity. This is useful if we allow partial updates (like a PATCH operation). In our design, we will treat PUT as full update (the client has to send both name and price in the ProductRequest). But we'll still demonstrate an update mapping method for variety. We'll ignore nulls in that case, just in case.

Alright, let's write the mapper interface:

### **ProductMapper.java**

 ```java
package com.example.springrest.mappers;

import com.example.springrest.dto.ProductRequest;
import com.example.springrest.dto.ProductResponse;
import com.example.springrest.models.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest dto);
    ProductResponse toResponse(Product entity);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Product target, ProductRequest source);
}
```

### Step-by-Step Breakdown

@Mapper(componentModel = "spring"): This tells MapStruct to generate an implementation (ProductMapperImpl) and make it a Spring bean (so that Spring's dependency injection can inject this mapper wherever needed). The componentModel = "spring" is important because it means MapStruct will use Spring's annotations to register the mapper (essentially it will add @Component to the implementation class).

The interface methods define mapping rules:

Product toEntity(ProductRequest dto): 
> MapStruct will generate code to create a Product from a ProductRequest. It sees that Product has fields name and price, and ProductRequest has name and price of compatible types, so it will map those. For id, since ProductRequest has no id, it will likely just leave id as default (null) which is what we want for a new entity (id will be generated).

ProductResponse toResponse(Product entity): 
> Similarly, it will generate code to create ProductResponse from Product. It matches id, name, price.

The interesting one: updateEntity(@MappingTarget Product target, ProductRequest source).
>This tells MapStruct to update an existing Product (@MappingTarget indicates the object to mutate) with values from ProductRequest. The @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) means if the source (ProductRequest) has any null properties, the mapper will not override the target's properties with null. This is a way to ignore nulls, which is useful if, say, we had an optional field and we only want to set fields that are present. In our case, since ProductRequest is for PUT (which should always have both fields non-null by validation), this is not strictly necessary, but if we ever used it for a PATCH scenario where maybe the client could send only one field, this would allow partial updates.

Inside this method, MapStruct will effectively generate:

```java
if ( source.getName() != null ) {
    target.setName( source.getName() );
}
if ( source.getPrice() != null ) {
    target.setPrice( source.getPrice() );
}
```

> Because our ProductRequest fields are never null (the record doesn't allow null by validation rules except you could theoretically pass null in JSON, but then validation catches it), this just acts like a normal copy for our usage. But good practice anyway.

We do not map id in update (MapStruct won't because ProductRequest has no id). That's good because we never want to override the id of an existing product.

We also don't return anything in updateEntity; it's a void method that mutates the target. We could have had it return Product for fluent usage, but MapStruct requires a void in this style I believe for proper @MappingTarget semantics.

### Using MapStruct
We have to add MapStruct as a dependency. In Maven, that's something like:

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.5.5.Final</version>
</dependency>
```

And we need to enable annotation processing in our IDE for Lombok and MapStruct to work. In IntelliJ, for instance, you ensure "Enable annotation processing" is on.

Once you build the project, MapStruct will generate ProductMapperImpl with implementations of those methods. We don't have to look at it, but it's basically straightforward code.

Now, whenever we need to convert between DTO and entity, we can just call mapper.toEntity(request) or mapper.toResponse(product), etc., as long as we have an instance of ProductMapper. Because we used componentModel = "spring", Spring will create a singleton of ProductMapperImpl and we can @Autowired it or, as we'll do, include it in constructor injection of our controller or service.

This greatly reduces boilerplate and risk of error in mapping. It also centralizes mapping logic. If, for example, we later add a field description to Product and ProductDTOs, we just update the DTO and entity, and MapStruct will automatically map if the names match. If names don't match or types need conversion, MapStruct allows annotations like @Mapping(source="something", target="otherName") or custom methods for converting types (like mapping an enum to a string). But in our simple case, it's straightforward.

### Alternatives
If we didn't use MapStruct, one could:

> Use Lombok's @Builder on the ProductResponse and then do something like ProductResponse.builder().id(p.getId()).name(p.getName())...build(). That's still manual for each field.

> Use ModelMapper, which at runtime would do modelMapper.map(product, ProductResponse.class). That's one line in code, but behind the scenes it's using reflection and type introspection each time.

> Write a custom constructor or static factory: e.g., ProductResponse.of(Product p) that returns a new ProductResponse(p.getId(), p.getName(), p.getPrice()). That's not bad either - in fact, small projects often do that. But again, it's manual and you have to write it for each DTO.

MapStruct is a bit of setup but then it feels like magic because it just handles things and if something doesn't match, you get a compile-time error.

Now we have:

> Entities (with JPA annotations).
>
> DTOs (with validation annotations).
>
> Mapper (to go between them).

Our next step is to interact with the database: that's where the **Repository layer** comes in.
