package ec.solmedia.course.infrastructure.persistence

import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseName
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.CourseRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class PostgreSQLCourseRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) : CourseRepository {

    override fun save(course: Course) {
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

    override fun find(id: CourseId) = try {
        val query = "SELECT * FROM course where id=:id"
        val params = MapSqlParameterSource().addValue("id", id.value.toString())
        jdbcTemplate.queryForObject(query, params, mapRow()) ?: throw CourseNotFound(id)
    } catch (error: Exception) {
        throw CourseNotFound(id)
    }

    private fun mapRow(): RowMapper<Course> {
        return RowMapper { rs: ResultSet, _: Int ->
            val id = CourseId(rs.getString("id"))
            val name = CourseName(rs.getString("name"))
            val createdAt = rs.getTimestamp("created_at").toLocalDateTime()
            Course(id, name, createdAt)
        }
    }
}
