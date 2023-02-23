package ec.solmedia.integration

import ec.solmedia.course.domain.Course
import ec.solmedia.course.infrastructure.persistence.PostgreSQLCourseRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.LocalDateTime
import kotlin.test.assertEquals

class PostgreSQLCourseRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: PostgreSQLCourseRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should save a course`() {
        val courseId = "13590efb-c181-4c5f-9f95-b768abde13e2"
        val courseToSave = Course.of(courseId, "Test", LocalDateTime.of(2023, 1, 1, 0, 0))

        repository.saveCourse(courseToSave)

        val courseFromDb = jdbcTemplate.queryForObject(
            "select * from course where id=?",
            mapRow(),
            courseId,
        )

        assertEquals(courseToSave, courseFromDb)
    }

    private fun mapRow(): RowMapper<Course> {
        return RowMapper { rs: ResultSet, _: Int ->
            val createdAt = rs.getTimestamp("created_at").toLocalDateTime()
            Course.of(rs.getString("id"), rs.getString("name"), createdAt)
        }
    }
}
