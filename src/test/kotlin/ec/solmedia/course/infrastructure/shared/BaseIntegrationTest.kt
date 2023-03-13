package ec.solmedia.course.infrastructure.shared

import ec.solmedia.shared.Application
import io.mockk.unmockkAll
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.junit.jupiter.api.AfterEach
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = [Application::class])
@ActiveProfiles("test")
class BaseIntegrationTest {

    companion object {
        @JvmStatic
        val postgresqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:14.5")
            .withDatabaseName("course_database")
            .withUsername("course_username")
            .withPassword("course_password")

        @PostConstruct
        fun start() {
            postgresqlContainer.start()
        }

        @PreDestroy
        fun stop() {
            postgresqlContainer.stop()
        }
    }

    @AfterEach
    protected fun cleanMock() {
        unmockkAll()
    }
}
