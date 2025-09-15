package ec.solmedia.coursesCounter.infrastructure.persistence

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ec.solmedia.course.domain.CourseId
import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterId
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository
import ec.solmedia.coursesCounter.domain.CoursesCounterTotal
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime

class PostgreSQLCoursesCounterRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) :
    CoursesCounterRepository {

    override fun search(): CoursesCounter? {
        val query = "SELECT * FROM courses_counter"
        val coursesCounter = jdbcTemplate.query(query, MapSqlParameterSource(), mapRow())
        return coursesCounter.firstOrNull()
    }

    private fun mapRow(): RowMapper<CoursesCounter> {
        return RowMapper { rs: ResultSet, _: Int ->
            val existingCoursesJson = rs.getString("existing_courses")
            val objectMapper = jacksonObjectMapper()
            val courseIds =
                objectMapper.readValue<List<String>>(existingCoursesJson).map { CourseId(it) }.toMutableList()

            CoursesCounter(
                id = CoursesCounterId(rs.getString("id")),
                total = CoursesCounterTotal(rs.getInt("total")),
                existingCourses = courseIds,
                createdAt = rs.getTimestamp("created_at").toLocalDateTime(),
            )
        }
    }

    override fun save(counter: CoursesCounter) {
        val query = """INSERT INTO courses_counter(id, total, existing_courses, created_at)
            VALUES (:id, :total, :existing_courses\:\:json, :created_at) ON CONFLICT (id) DO UPDATE SET
            total = :total, existing_courses = :existing_courses\:\:json"""

        val objectMapper = jacksonObjectMapper()
        val existingCourses = objectMapper.writeValueAsString(counter.existingCourses.map { it.value })

        MapSqlParameterSource()
            .addValue("id", counter.id.value)
            .addValue("total", counter.total.value)
            .addValue("existing_courses", existingCourses)
            .addValue("created_at", Timestamp.valueOf(LocalDateTime.now()))
            .let { params ->
                jdbcTemplate.update(query, params)
            }
    }
}
