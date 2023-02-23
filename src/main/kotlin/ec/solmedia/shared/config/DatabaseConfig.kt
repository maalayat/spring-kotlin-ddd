package ec.solmedia.shared.config

import ec.solmedia.course.infrastructure.persistence.PostgreSQLCourseRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class DatabaseConfig {

    @Bean
    fun courseRepository(jdbcTemplate: NamedParameterJdbcTemplate) = PostgreSQLCourseRepository(jdbcTemplate)
}
