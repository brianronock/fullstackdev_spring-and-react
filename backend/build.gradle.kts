plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
    id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

repositories { mavenCentral() }

dependencies {
    // --- Chapter 1 parity ---
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // --- Chapter 3 additions ---
    //implementation("org.springframework.boot:spring-boot-starter-security")
    //implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    //runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    //runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    //implementation("org.springframework.boot:spring-boot-starter-cache")
    //implementation("org.springframework.boot:spring-boot-starter-data-redis")
    //implementation("io.micrometer:micrometer-registry-prometheus")
    //implementation("com.github.vladimir-bukhtoyarov:bucket4j-core:8.10.1")
    //implementation("com.bucket4j:bucket4j_jdk17-core:8.15.0")
    //implementation("org.flywaydb:flyway-core")

    //testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.1"))
    //testImplementation("org.testcontainers:junit-jupiter")
    //testImplementation("org.testcontainers:postgresql")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports { xml.required.set(true); html.required.set(true); csv.required.set(false) }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
}
