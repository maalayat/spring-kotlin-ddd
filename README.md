# Spring Kotlin DDD ☕

A comprehensive **Domain-Driven Design (DDD)** project built with **Spring Boot** and **Kotlin**, demonstrating modern
software architecture patterns and best practices for building scalable, maintainable applications.

## 🏗️ Architecture Overview

This project implements **Hexagonal Architecture** (Ports & Adapters) with clear separation of concerns across multiple
bounded contexts:

### Bounded Contexts

1. **Course Context** (`ec.solmedia.course`)
    - Manages course creation and retrieval
    - Publishes domain events when courses are created
    - Implements value objects for type safety (`CourseId`, `CourseName`)

2. **CoursesCounter Context** (`ec.solmedia.coursesCounter`)
    - Tracks the total number of courses created
    - Listens to course creation events
    - Maintains an aggregate counter with course history

3. **Shared Kernel** (`ec.solmedia.shared`)
    - Common domain primitives and infrastructure
    - Event bus implementation
    - Application configuration

### Layer Structure

Each bounded context follows the same layered architecture:

```
├── domain/          # Business logic, entities, value objects, domain events
├── application/     # Use cases, application services
└── infrastructure/  # External concerns (REST, persistence, messaging)
```

## 🚀 Technology Stack

- **Framework**: Spring Boot 3.4.4
- **Language**: Kotlin 1.9.25 with Java 21
- **Database**: PostgreSQL with Flyway migrations
- **Error Handling**: Exception-based error handling with custom domain exceptions
- **Testing**: JUnit 5, [TestContainers](https://www.testcontainers.org/), [REST-assured](https://rest-assured.io/),
  MockK
- **Code Quality**: [Ktlint](https://ktlint.github.io/) via [Spotless](https://github.com/diffplug/spotless)
- **Build Tool**: Gradle with Kotlin DSL
- **Containerization**: Docker Compose for local development

## 🔧 Key Features

- **Domain Events**: Event-driven communication between bounded contexts
- **Exception Handling**: Custom domain exceptions with Spring's `@RestControllerAdvice`
- **Value Objects**: Strong typing with validation for domain concepts
- **Repository Pattern**: Clean data access abstraction
- **Integration Testing**: Comprehensive testing with TestContainers
- **Database Migrations**: Flyway for schema versioning
- **Health Checks**: Spring Actuator endpoints

## 🚀 Getting Started

### Prerequisites

- Java 21
- Docker and Docker Compose
- Git

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/maalayat/spring-kotlin-ddd
   cd spring-kotlin-ddd
   ```

2. **Start the database (Optional. Step 4 also automatically starts Docker)**
   ```bash
   docker-compose up -d postgres
   ```

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080`

### Database Setup

The project uses PostgreSQL with the following default configuration:

- **Host**: localhost:5432
- **Database**: course_database
- **Username**: course_username
- **Password**: course_password

You can override these values using environment variables:

- `POSTGRESQL_URL`
- `POSTGRESQL_PORT`
- `POSTGRESQL_PASSWORD`

## 📡 API Endpoints

### Health Check

```http
GET /actuator/health
```

### Course Management

```http
# Create a new course
POST /course
Content-Type: application/json

{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Introduction to Kotlin"
}

# Get course by ID
GET /course/{courseId}
```

## 🧪 Testing

### Run All Tests

```bash
./gradlew test
```

### Code Quality Checks

```bash
# Run all quality checks
./gradlew check

# Check code formatting
./gradlew spotlessCheck

# Auto-fix formatting issues
./gradlew spotlessApply
```

## 🏗️ Development

### Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   └── ec/solmedia/
│   │       ├── course/           # Course bounded context
│   │       ├── coursesCounter/   # Counter bounded context
│   │       └── shared/           # Shared kernel
│   └── resources/
│       ├── application.yml       # Application configuration
│       └── db/migration/         # Flyway migrations
└── test/
    ├── kotlin/                   # Unit and integration tests
    └── resources/
```

### Adding New Features

1. **Domain First**: Start by modeling the domain in the appropriate bounded context
2. **Use Cases**: Implement application services for business operations
3. **Infrastructure**: Add REST controllers, repositories, or event handlers as needed
4. **Tests**: Write comprehensive tests at all layers

### Code Style

This project follows Kotlin coding conventions enforced by Ktlint:

- Use meaningful names for classes, functions, and variables
- Prefer immutable data structures
- Leverage Kotlin's type system for domain modeling
- Use custom domain exceptions for error handling

## 🔍 Domain Model Examples

### Course Aggregate

```kotlin
data class Course(
    val id: CourseId,
    val name: CourseName,
    val createdAt: LocalDateTime,
    val domainEvents: MutableList<DomainEvent>
)
```

### Value Objects with Validation

```kotlin
data class CourseName private constructor(val value: String) {
    companion object {
        operator fun invoke(name: String): CourseName {
            if (name.isEmpty() || name.isBlank()) {
                throw InvalidCourseName(name)
            }
            return CourseName(name)
        }
    }
}
```

## 📚 Learning Resources

### Domain-Driven Design

- [Domain-Driven Design by Eric Evans](https://www.domainlanguage.com/ddd/)
- [Implementing Domain-Driven Design by Vaughn Vernon](https://kalele.io/books/)

### Kotlin & Spring Boot

- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

### Testing

- [Test code using JUnit in JVM - tutorial](https://kotlinlang.org/docs/jvm-test-using-junit.html)
- [TestContainers Documentation](https://www.testcontainers.org/)
- [REST-assured Documentation](https://rest-assured.io/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes following the project's coding standards
4. Run tests and quality checks (`./gradlew check`)
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

## 📄 License

This project is part of a Domain-Driven Design course and is intended for educational purposes.
