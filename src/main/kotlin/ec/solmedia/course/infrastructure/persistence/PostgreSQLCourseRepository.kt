package ec.solmedia.course.infrastructure.persistence

import arrow.core.raise.Raise
import arrow.core.raise.catch
import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseName
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.InvalidCourse
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class PostgreSQLCourseRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CourseRepository {

    override fun saveCourse(course: Course) {
        MapSqlParameterSource()
            .addValue("id", course.id.value)
            .addValue("name", course.name.value)
            .addValue("createdAt", course.createdAt)
            .let { params ->
                jdbcTemplate.update(
                    "INSERT INTO course (id, name, created_at) VALUES (:id,:name,:createdAt)",
                    params,
                )
            }
    }

    context(Raise<CourseApplicationError>)
    override fun find(id: CourseId): Course = catch({
        val query = "SELECT * FROM course where id=:id"
        val params = MapSqlParameterSource().addValue("id", id.value.toString())
        jdbcTemplate.queryForObject(query, params, mapRow())
            ?: raise(CourseNotFound(id))
    }) { raise(CourseNotFound(id)) }

    context(Raise<InvalidCourse>)
    private fun mapRow(): RowMapper<Course> {
        return RowMapper { rs: ResultSet, _: Int ->
            val id = CourseId(rs.getString("id"))
            val name = CourseName(rs.getString("name"))
            val createdAt = rs.getTimestamp("created_at").toLocalDateTime()
            Course(id, name, createdAt)
        }
    }
}
