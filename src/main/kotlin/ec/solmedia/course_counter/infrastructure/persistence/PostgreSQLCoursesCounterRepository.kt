package ec.solmedia.course_counter.infrastructure.persistence

import arrow.core.Either
import arrow.core.identity
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course_counter.domain.CoursesCounter
import ec.solmedia.course_counter.domain.CoursesCounterId
import ec.solmedia.course_counter.domain.CoursesCounterRepository
import ec.solmedia.course_counter.domain.CoursesCounterTotal
import org.postgresql.util.PGobject
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.time.LocalDateTime


class PostgreSQLCoursesCounterRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val objectMapper: ObjectMapper = ObjectMapper()
) : CoursesCounterRepository {

    override fun save(coursesCounter: CoursesCounter) {
        val jsonString = objectMapper.writeValueAsString(coursesCounter.existingCourses)
        val jsonObject = PGobject()
        jsonObject.type = "jsonb"
        jsonObject.value = jsonString

        MapSqlParameterSource()
            .addValue("id", coursesCounter.id.value)
            .addValue("total", coursesCounter.total.value)
            .addValue("existing_courses", jsonObject)
            .addValue("created_at", LocalDateTime.now())
            .let { params ->
                jdbcTemplate.update(
                    "INSERT INTO courses_counter (id, total, existing_courses, created_at) VALUES (:id,:total,:existing_courses,:created_at)",
                    params,
                )
            }
    }

    override fun search(): CoursesCounter? {
        val query = "SELECT * FROM courses_counter LIMIT 1"
        return jdbcTemplate.queryForObject(query, MapSqlParameterSource(), mapRow())

    }

    private fun mapRow(): RowMapper<CoursesCounter> /*= either*/ {
        return RowMapper { rs, _ ->
            val existingCoursesJson = rs.getString("existing_courses")

            val existingCourses = objectMapper.readValue(existingCoursesJson,
                object : TypeReference<MutableList<CourseId>>() {}
            )

            CoursesCounter(
                id = CoursesCounterId(rs.getString("id")),
                total = CoursesCounterTotal(rs.getInt("total")),
                existingCourses = existingCourses,
            )
        }
    }/*.foldRight()*/


}

fun <L, R> Either<L, R>.foldRight(): R {
    return fold({ throw RuntimeException() }, ::identity)
}