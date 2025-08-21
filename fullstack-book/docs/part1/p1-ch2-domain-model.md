---
id: p1-ch2-domain-model
title: "Chapter 2"
description: "Defining the Domain Model with JPA"
---

# Chapter 2

## **Defining the Domain Model with JPA**

Let<span dir="rtl">’</span>s define the core data of our application:
the **Product** entity. In a sense, this is the heart of the backend,
because everything we do (listing products, saving new ones, etc.)
revolves around this entity.

### **Understanding JPA and Entities**

**JPA (Java Persistence API)** is a specification in Java for
object-relational mapping (ORM). Spring Data JPA is an implementation
that uses an ORM provider (like Hibernate under the hood) to let us work
with database data as Java objects. Instead of writing SQL, you can
define entity classes and use a repository interface to handle common
operations. JPA handles translating between the object world and the
relational world:

> We annotate a Java class as an @Entity, which means it maps to a
> database table.
>
> Fields in the class map to columns in the table.
>
> An @Id field is the primary key, and we can have it auto-generated (so
> the database assigns IDs).
>
> Relationships between entities (not in this simple app, but in others)
> can be mapped with annotations like @OneToMany, etc.

### **Why use JPA (ORM) instead of plain JDBC (SQL)?** 
Productivity and
maintainability. Writing SQL by hand for every query is fine in simple
cases, but as the application grows, it can lead to a lot of boilerplate
code (opening connections, mapping ResultSets to objects, etc.). ORMs
handle this mapping layer for you and allow using a repository pattern
or query methods. They also offer database-agnostic features (you could
switch from H2 to MySQL by changing a URL and driver, without changing
your repository code). The trade-off is that ORMs can sometimes be less
efficient than hand-tuned SQL for very complex queries, and you need to
understand how they work to avoid pitfalls like the
<span dir="rtl">“</span>N+1 query problem.” But for the majority of CRUD
operations, JPA is a huge help.

### **Modeling the Product Entity**

We want to store products with at least a few fields:

> **id**: A unique identifier for each product.
>
> **name**: The name of the product.
>
> **price**: The price of the product.

For simplicity, we<span dir="rtl">’</span>ll stick to these three
fields. In a real system, you might have more (description, quantity,
category, etc.), but these will illustrate all the key concepts.

### **Choosing Data Types**

id will be a Long (a 64-bit integer). We mark it as auto-generated. In
the database, this might be an IDENTITY column (auto-increment). Using
a numeric ID is straightforward; alternative is a UUID, but
that<span dir="rtl">’</span>s overkill here.

 name will be a String (varchar in the database). We want to ensure
 it<span dir="rtl">’</span>s not empty and not overly long.
 Let<span dir="rtl">’</span>s cap it at 120 characters for this
 example. We<span dir="rtl">’</span>ll add a validation annotation to
 ensure it<span dir="rtl">’</span>s not blank.

 price will be a **BigDecimal**. This is important: money should not be
 represented as a double or float in Java. Floating-point types are
 binary fractions and can<span dir="rtl">’</span>t accurately represent
 many decimal values (for example, 0.1 + 0.2 might end up as
 0.3000000004 due to binary rounding). BigDecimal is an
 arbitrary-precision decimal number that is perfect for currency.
 We<span dir="rtl">’</span>ll configure it to have 2 decimal places
 (cents). Another approach some use is to store prices as an integer
 number of cents (like 1250 for \$12.50), but BigDecimal is more
 expressive.

 We also want to ensure price is not null (required) and ideally
 positive (though we might enforce positivity via validation on the
 DTO, and just ensure not null on the entity).

We will use Jakarta Validation (JSR 380, which in Spring Boot 3 is under
jakarta.validation.constraints) on the entity fields just as a second
line of defense for database integrity. (We<span dir="rtl">’</span>ll
also validate at the DTO level for early feedback, but having it on the
entity means even if someone bypasses our controller and uses the
repository directly, they can<span dir="rtl">’</span>t save an invalid
product without an exception.)

Let<span dir="rtl">’</span>s see what the code for our **Product.java**
Entity looks like, then we<span dir="rtl">’</span>ll break down the
annotations:

### **Product.java**

```java
package com.example.springrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = “products")
@Getter @Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is Mandatory")
    @Column(nullable = false, length = 120)
    private String name;
    
    @NotNull(message = "Price must be provided")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    
    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

}
```

### **Step-by-Step Breakdown**

> **Class and Lombok Annotations**: We annotate the class with @Entity,
> which tells JPA that this is a persistent class. @Table(name =
> "products") explicitly sets the table name (otherwise it might default
> to Product which might or might not be pluralized depending on
> settings). We use Lombok<span dir="rtl">’</span>s @Getter and @Setter
> to auto-generate getters and setters for all fields, and
> @NoArgsConstructor to generate a no-argument constructor (which JPA
> requires to instantiate objects via reflection). We also provide a
> convenience constructor with name and price for easy creation in code.

 **Primary Key (id)**:

> @Id marks this field as the primary key.

> @GeneratedValue(strategy = GenerationType.IDENTITY) means the database
> will automatically generate a value for this ID (IDENTITY is typically
> auto-increment). Other strategies include AUTO (let Hibernate choose),
> SEQUENCE (using a database sequence object), or TABLE (using a table
> for ids), but IDENTITY works well with many databases (H2, MySQL,
> PostgreSQL<span dir="rtl">’</span>s SERIAL, etc.). Essentially, when
> we save a new Product without an id, the DB will assign one, and JPA
> will fetch that back into this field.
>
 **Name**:
>
> @NotBlank(message = "Name is mandatory") is a validation constraint
> that ensures the string is not null and, after trimming, not empty.
> This covers cases like "" or "   " as invalid. We put a custom message
> for clarity.

> @Column(nullable = false, length = 120) maps this field to a column
> that does not allow nulls and has a max length of 120 characters. The
> length helps the database set up the column (e.g., VARCHAR(120)).

> By combining these, we ensure at the database schema level name is
> required, and at the Java object level, it should never be blank.

 **Price**:
>
> @NotNull(message = "Price must be provided") ensures the price is not
> null.

> @Column(nullable = false, precision = 12, scale = 2) configures the
> SQL column to have a precision of 12 and scale of 2. This means the
> number can have up to 12 digits in total, with 2 of them after the
> decimal point. So the largest value would be 10 digits before decimal
> and 2 after, e.g., 99,999,999,999.99 (which is 100 billion minus a
> cent). This is plenty for our example. Essentially,
> it<span dir="rtl">’</span>s like saying <span dir="rtl">“</span>use a
> DECIMAL(12,2)” in SQL. The nullable = false again ensures the column
> is not null in the database.

> We chose BigDecimal for reasons mentioned (financial calculations). If
> you<span dir="rtl">’</span>re curious, using double for money can lead
> to subtle bugs - for instance, summing 0.1 ten times might not give
> exactly 1.0 due to binary floating point errors. BigDecimal avoids
> that by using decimal arithmetic or integer under the hood. Another
> approach is to store everything in the smallest unit (cents) as an
> integer (e.g., store 12.50 as 1250 in an integer field), but then you
> have to remember to divide by 100 for display; BigDecimal is more
> straightforward albeit slightly more verbose to use.

> We included a no-arg constructor (Lombok gave us one) because
> frameworks like JPA need it. The custom constructor with (name, price)
> is just for convenience when creating objects in code or tests.

With this entity defined, JPA (via Hibernate) will handle creating the
table schema if we use spring.jpa.hibernate.ddl-auto=update in
development (it can create the table automatically). However, relying on
auto-DDL is not recommended for production, so later
we<span dir="rtl">’</span>ll discuss using a migration tool (Flyway) for
database schema.

### **Validations in Entity vs DTO**
We put @NotBlank and @NotNull on the
entity as a safeguard. But often we also (or instead) put validations on
the DTOs that the controllers use for input (ProductRequest DTO in our
case). Why both? Validation on DTO gives faster feedback (the controller
can catch it before even trying to map to an entity or hitting the
database). Validation on the entity can serve as a last line of defense
(if some misuse or a different input path tries to save an entity, the
entity itself enforces some invariants). In this project,
we<span dir="rtl">’</span>ll do both to illustrate, but some developers
choose one or the other.

One thing to note: the @NotBlank on name will ensure that when Hibernate
checks the entity (during persist or merge) it<span dir="rtl">’</span>s
not blank. However, by default, JPA provider might not automatically
validate on persist unless configured
(spring.jpa.properties.javax.persistence.validation.mode=callback or
similar). Spring Data will validate entities on repository save if the
object is managed by Spring (since we have the validation on the object
and use @Valid on inputs typically). This is a bit nuanced, but suffice
it to say, our primary validation will happen at the API level, and the
annotations on the entity mainly help for database schema and
documentation of intent.

### **Pitfalls to Avoid**
One common mistake is forgetting to include a
no-argument constructor (JPA requires it). With Lombok,
@NoArgsConstructor handles that. Another is not marking ID generation
correctly; if you don<span dir="rtl">’</span>t set generation,
you<span dir="rtl">’</span>d have to manually set IDs. Using IDENTITY
makes life easier. Also, be careful with BigDecimal: when setting the
scale (2 decimals) on the column, ensure you always provide values with
at most 2 decimals, or set the scale on the BigDecimal in code,
otherwise you might get a runtime error if you attempt to store a number
with more decimal places than allowed.

At this point, we have our core **domain model**. Next,
we<span dir="rtl">’</span>ll create the DTOs to define what our API
exchanges as input and output.
