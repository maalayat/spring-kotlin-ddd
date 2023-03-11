import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.diffplug.spotless") version "6.14.1"
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("plugin.spring") version "1.7.22"
    id("org.flywaydb.flyway") version "9.14.1"
    application
}

group = "ec.solmedia"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

sourceSets {
    create("test-integration") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val testIntegrationImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["testIntegrationRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    testClassesDirs = sourceSets["test-integration"].output.classesDirs
    classpath = sourceSets["test-integration"].runtimeClasspath
    useJUnitPlatform()
    shouldRunAfter("test")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spotless
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.14.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.14.1")
    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql:42.5.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:1.13.4")

    // Test containers
    testIntegrationImplementation("org.testcontainers:testcontainers:1.17.6")
    testIntegrationImplementation("org.testcontainers:jdbc:1.17.6")
    testIntegrationImplementation("org.testcontainers:junit-jupiter:1.17.6")
    testIntegrationImplementation("org.testcontainers:postgresql:1.17.6")

    // Rest Assured
    testIntegrationImplementation("io.rest-assured:rest-assured:5.3.0")
    testIntegrationImplementation("io.rest-assured:kotlin-extensions:5.3.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlin {
        ktlint()
            .userData(
                mapOf(
                    "insert_final_newline" to "true",
                ),
            )
    }
    kotlinGradle {
        ktlint()
    }
}

tasks.check {
    dependsOn(integrationTest)
    dependsOn(tasks.spotlessCheck)
}

flyway {
    val host = System.getenv("POSTGRESQL_URL") ?: "localhost"
    val port = "5432"

    url = "jdbc:postgresql://$host:$port/course_database"
    user = System.getenv("POSTGRESQL_USERNAME") ?: "course_username"
    password = System.getenv("POSTGRESQL_PASSWORD") ?: "course_password"
}
