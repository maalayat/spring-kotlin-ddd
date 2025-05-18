package ec.solmedia.shared.config

import ec.solmedia.course.infrastructure.persistence.PostgreSQLCourseRepository
import ec.solmedia.course_counter.infrastructure.persistence.PostgreSQLCoursesCounterRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Configuration
class DatabaseConfig {

    @Bean
    fun courseRepository(jdbcTemplate: NamedParameterJdbcTemplate) =
        PostgreSQLCourseRepository(jdbcTemplate)

    @Bean
    fun coursesCounterRepository(jdbcTemplate: NamedParameterJdbcTemplate) =
        PostgreSQLCoursesCounterRepository(jdbcTemplate)
}
