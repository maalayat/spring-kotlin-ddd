package ec.solmedia.course.infrastructure.shared

import ec.solmedia.shared.Application
import io.mockk.unmockkAll
import io.restassured.RestAssured
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.junit.jupiter.api.AfterEach
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
class BaseAcceptanceTest {

    @LocalServerPort
    private val springbootPort: Int = 0

    companion object {
        private const val POSTGRESQL_PORT = 5432
        val environment: DockerComposeContainer<*> =
            DockerComposeContainer(File("docker-compose-test.yml"))
                .withOptions("--compatibility")
                .withExposedService("db", POSTGRESQL_PORT, Wait.forListeningPort())
                .withLocalCompose(true)

        @PostConstruct
        fun start() {
            environment.start()
        }

        @PreDestroy
        fun stop() {
            environment.stop()
        }
    }

    @BeforeEach
    fun setUp() {
        RestAssured.port = springbootPort
    }

    @AfterEach
    protected fun cleanMock() {
        unmockkAll()
    }
}
