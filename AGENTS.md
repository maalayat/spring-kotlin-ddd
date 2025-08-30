# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Essential Commands

### Build and Development
- **Build project:** `./gradlew build`
- **Run checks (lint + tests):** `./gradlew check`
- **Lint check only:** `./gradlew spotlessCheck`
- **Auto-fix style:** `./gradlew spotlessApply`
- **Integration tests:** `./gradlew integrationTest`
- **Single test:** `./gradlew test --tests "ClassName.methodName"`

### Database
- **Run migrations:** `./gradlew flywayMigrate`
- **Migrations location:** `src/main/resources/db/migration/`

## Architecture Overview

This is a Spring Boot + Kotlin project implementing Domain-Driven Design principles for course management.

### DDD Structure
```
ec.solmedia.course/
├── application/     # Use cases (CourseCreator, CourseFinder)
├── domain/         # Entities, value objects, repository interfaces
└── infrastructure/
    ├── persistence/ # Repository implementations
    └── rest/       # Controllers and REST adapters

ec.solmedia.shared/  # Cross-cutting concerns, DI configuration
```

### Key Patterns
- **Value Objects:** Domain entities use Arrow Core's context receivers for validation (e.g., `CourseId`, `CourseName`)
- **Repository Pattern:** Interfaces in domain, implementations in infrastructure
- **Functional Error Handling:** Arrow Core `Raise` context for type-safe error handling
- **Dependency Injection:** Spring beans configured in `shared/config/DependencyInjectionConf.kt`

## Technology Stack
- **Language:** Kotlin with Arrow Core functional programming
- **Framework:** Spring Boot 3.4.4
- **Database:** PostgreSQL (H2 for tests)
- **Testing:** JUnit 5, MockK, Testcontainers, REST-assured
- **Build:** Gradle with Spotless (Ktlint)

## Testing Structure
- **Unit tests:** Mirror main package structure under `test/`
- **Integration tests:** Use `BaseIntegrationTest` with Testcontainers
- **Acceptance tests:** Use `BaseAcceptanceTest` or `BaseRestAssuredTest`
- **Test data:** Mother objects pattern (e.g., `CourseMother.kt`, `UuidMother.kt`)
- **Test fixtures:** SQL files in `src/test/resources/db/fixtures/`

## Configuration
- **Main config:** `application.yml` (PostgreSQL with environment variables)
- **Test config:** `application-test.yml` (Testcontainers JDBC)
- **Database migrations:** Flyway with environment-based connection

## Development Workflow
1. Always run `./gradlew spotlessApply` before committing
2. Use Arrow Core's `Raise` context for error handling in domain logic
3. Add new use cases to `DependencyInjectionConf.kt`
4. Create database migrations for schema changes
5. Follow Mother pattern for test data creation

## Code Conventions
- Kotlin with functional programming patterns via Arrow Core
- Context receivers for validation in value objects
- Repository pattern with domain interfaces
- Spring dependency injection for application services