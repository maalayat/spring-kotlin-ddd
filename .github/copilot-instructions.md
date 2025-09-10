# Copilot Instructions for AI Agents

## Project Overview

This is a Kotlin/Spring Boot project following Domain-Driven Design (DDD) principles for a course management domain. The
codebase is organized by bounded contexts and layers (application, domain, infrastructure, shared).

## Key Architectural Patterns

- **DDD Structure:**
    - `ec/solmedia/course/` contains the main domain logic for courses.
        - `application/`: Use cases (e.g., `CourseCreator.kt`, `CourseFinder.kt`)
        - `domain/`: Entities, exceptions, repository interfaces
        - `infrastructure/`: Adapters (e.g., persistence, REST)
    - `ec/solmedia/shared/`: Cross-cutting concerns and shared utilities
- **Testing:**
    - Unit and integration tests are under `test/` mirroring the main structure
    - Test data/fixtures in `test/db/fixtures/`

## Developer Workflows

- **Build:** `./gradlew build`
- **Check (Lint, Style, Tests):** `./gradlew check`
    - Lint: `./gradlew spotlessCheck`
    - Auto-fix style: `./gradlew spotlessApply`
- **Configuration:**
    - App configs in `application.yml` and `application-test.yml` (in both `src/main/resources` and `bin/main/`)
    - Database migrations in `db/migration/`

## Project-Specific Conventions

- **Kotlin + Arrow Core:** Use functional error handling and value types from Arrow where possible
- **Repository Pattern:** Domain repositories are interfaces in `domain/`, implemented in `infrastructure/persistence/`
- **Dependency Injection:** Managed via Spring, configured in `shared/config/DependencyInjectionConf.kt`
- **REST API:** REST controllers/adapters live in `infrastructure/rest/`
- **Test Patterns:** Use `Mother` objects (e.g., `CourseMother.kt`, `UuidMother.kt`) for test data creation

## Integration Points

- **Database:** Managed via migrations in `db/migration/`; test fixtures in `test/db/fixtures/`
- **External Libraries:**
    - [Test Containers](https://www.testcontainers.org/) for integration testing
    - [Arrow Core](https://arrow-kt.io/docs/core/) for functional programming
    - [REST-assured](https://rest-assured.io/) for REST API testing

## Examples

- To add a new use case, create a class in `application/` and wire it in `DependencyInjectionConf.kt`
- To add a new entity, define it in `domain/` and update the repository interface/implementation
- To add a migration, place a new SQL file in `db/migration/`

## References

- See `README.md` for build and test commands
- See `shared/config/DependencyInjectionConf.kt` for DI setup
- See `test/` for test structure and patterns

---
For questions about project structure or conventions, review this file and the `README.md` before making changes.
