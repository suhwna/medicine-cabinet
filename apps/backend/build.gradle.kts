plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")     // JPA
	implementation("org.springframework.boot:spring-boot-starter-web")          // Web
	testImplementation("org.springframework.boot:spring-boot-starter-test")     // Test

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

	// Database
	runtimeOnly("org.postgresql:postgresql:42.7.5")                             // PostgreSQL
	implementation("org.springframework.boot:spring-boot-starter-data-redis")   // Redis

	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.1")        // JUnit

    implementation("com.auth0:java-jwt:4.5.0")                                  // JWT

    implementation("org.springframework.boot:spring-boot-starter-security")     // Spring Security

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")   // Spring MVC에서 Swagger UI 및 OpenAPI 문서 제공

}

tasks.withType<Test> {
	useJUnitPlatform()
}
