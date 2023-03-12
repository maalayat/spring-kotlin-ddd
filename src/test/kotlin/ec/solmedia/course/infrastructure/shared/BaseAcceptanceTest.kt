package ec.solmedia.course.infrastructure.shared

import ec.solmedia.shared.Application
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(classes = [Application::class])
class BaseAcceptanceTest {

    companion object {
        private const val POSTGRESQL_PORT = 5432
        val environment: DockerComposeContainer<*> =
            DockerComposeContainer(File("docker-compose-test.yml"))
                .withOptions("--compatibility")
                .withExposedService("db", POSTGRESQL_PORT, Wait.forListeningPort())
                .withLocalCompose(true)
    }

    @PostConstruct
    fun start() {
        environment.start()
    }

    @PreDestroy
    fun stop() {
        environment.stop()
    }
}
