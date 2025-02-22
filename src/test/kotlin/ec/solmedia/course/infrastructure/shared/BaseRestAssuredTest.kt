package ec.solmedia.course.infrastructure.shared

import ec.solmedia.shared.Application
import io.mockk.unmockkAll
import io.restassured.RestAssured
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(classes = [Application::class])
class BaseRestAssuredTest {

    @LocalServerPort
    private val springbootPort: Int = 0

    companion object {
        private const val POSTGRESQL_PORT = 5432
        val environment: DockerComposeContainer<*> =
            DockerComposeContainer(File("docker-compose-test.yml"))
                .withExposedService("db", POSTGRESQL_PORT, Wait.forListeningPort())
                .withLocalCompose(true)
                .withLogConsumer("db") { print(it.utf8String) }

        @JvmStatic
        @BeforeAll
        fun start() {
            println("Starting Docker Compose...")
            environment.start()
            println("Docker Compose started successfully.")
        }

        @JvmStatic
        @AfterAll
        fun stop() {
            println("Stopping Docker Compose...")
            environment.stop()
            println("Docker Compose stopped successfully.")
        }
    }

    @BeforeEach
    fun setUp() {
        RestAssured.port = springbootPort
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }
}
