package ec.solmedia.course.infrastructure.shared

import ec.solmedia.shared.Application
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@JdbcTest
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(classes = [Application::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BaseIntegrationTest {

    companion object {
        @Container
        private val postgresqlContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:14.5")

        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
        }
    }

    @AfterEach
    protected fun cleanMock() {
        unmockkAll()
    }
}
