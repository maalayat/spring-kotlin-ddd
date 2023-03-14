# spring-kotlin-ddd ☕

Domain-Driven Design course accompaniment project

- Spring Boot
- Gradle
- Kotlin
- [Test Containers](https://www.testcontainers.org/)
- [Arrow Core](https://arrow-kt.io/docs/core/)
- [REST-assured](https://rest-assured.io/)

## Build
Build the project for the first time: `./gradlew build`

## Check
Run all the checks: `./gradlew check`. This will do some checks that you can perform with isolated commands:

- [Klint](https://ktlint.github.io/) using [Spotless](https://github.com/diffplug/spotless): `./gradlew spotlessCheck`. 
- Fix style issues automatically: `./gradlew spotlessApply`.
- [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/): `./gradlew integrationTest`.

## Resources

### Kotlin

* [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
* [Comparison between Kotlin and Java](https://kotlinlang.org/docs/comparison-to-java.html)

### Kotlin test

* [Test code using JUnit in JVM - tutorial](https://kotlinlang.org/docs/jvm-test-using-junit.html)
* [JUnit5 assertions](https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html)
