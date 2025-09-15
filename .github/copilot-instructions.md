
# Copilot Instructions for AI Agents

## Project Overview

This repository is a Kotlin/Spring Boot project for course management, structured using Domain-Driven Design (DDD) principles. The codebase is organized by bounded contexts and layers, with a strong separation between domain logic, application use cases, infrastructure adapters, and shared utilities.

## Architecture & Structure

- **DDD Layering:**
    - `ec/solmedia/course/` — Main domain logic for courses
        - `application/`: Use cases (e.g., `CourseCreator.kt`, `CourseFinder.kt`)
        - `domain/`: Entities, value objects, repository interfaces, custom exceptions
        - `infrastructure/`: Adapters (persistence, REST controllers)
    - `ec/solmedia/shared/`: Cross-cutting concerns, DI config (`DependencyInjectionConf.kt`)
- **Testing:**
    - Unit/integration tests mirror main structure under `test/`
    - Test fixtures in `test/db/fixtures/`
    - Test data uses the Mother pattern (e.g., `CourseMother.kt`)

## Developer Workflows

- **Build:** `./gradlew build`
- **Lint/Style/Test:** `./gradlew check` (includes lint, style, and tests)
    - Lint only: `./gradlew spotlessCheck`
    - Auto-fix style: `./gradlew spotlessApply`
- **Run single test:** `./gradlew test --tests "ClassName.methodName"`
- **Database migrations:** Place SQL in `src/main/resources/db/migration/`, run with `./gradlew flywayMigrate`
- **Config:** App configs in `application.yml` and `application-test.yml` (in both `src/main/resources` and `bin/main/`)

## Project-Specific Conventions

- **Value Objects:** Domain entities/VOs validate on creation and throw custom exceptions (see `CourseId`, `CourseName`)
- **Repository Pattern:** Interfaces in `domain/`, implementations in `infrastructure/persistence/`
- **Dependency Injection:** Spring beans configured in `shared/config/DependencyInjectionConf.kt`
- **REST API:** Controllers/adapters in `infrastructure/rest/`
- **Exception Handling:** Custom domain exceptions, handled by `@RestControllerAdvice` (see REST layer)
- **Testing:** Use Mother objects for test data; base test classes for integration/acceptance tests

## Integration Points & External Dependencies

- **Database:** PostgreSQL (H2 for tests); migrations via Flyway (`db/migration/`)
- **Testing:**
    - [Testcontainers](https://www.testcontainers.org/) for DB integration
    - [REST-assured](https://rest-assured.io/) for REST API testing
- **Functional Programming:** [Arrow Core](https://arrow-kt.io/docs/core/) for value types and error handling

## Examples & Patterns

- **Add a use case:** Create in `application/`, wire in `DependencyInjectionConf.kt`
- **Add an entity:** Define in `domain/`, update repository interface/implementation
- **Add a migration:** Place SQL in `db/migration/`
- **Test data:** Use Mother objects (e.g., `CourseMother.kt`)

## References

- `README.md`: Build/test commands, project overview
- `AGENTS.md`: Additional agent guidance
- `shared/config/DependencyInjectionConf.kt`: DI setup
- `test/`: Test structure and patterns

---
For questions about structure or conventions, review this file, `AGENTS.md`, and `README.md` before making changes.
