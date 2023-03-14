package ec.solmedia.course.infrastructure.acceptance

import ec.solmedia.course.domain.CourseMother
import ec.solmedia.course.infrastructure.persistence.PostgreSQLCourseRepository
import ec.solmedia.course.infrastructure.shared.BaseAcceptanceTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql

class GetFindCourseControllerRestAssuredAcceptanceTest : BaseAcceptanceTest() {

    @Autowired
    private lateinit var courseRepository: PostgreSQLCourseRepository

    @Test
    @Sql("classpath:db/fixtures/find/add-course-data.sql")
    fun `should find course successfully with fixture`() {
        When {
            get("/course/${course.id}")
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            body().asString().compareTo(existingCourseResponse)
        }
    }

    @Test
    fun `should find course successfully with course creation`() {
        Given {
            `an existing course`()
            contentType(ContentType.JSON)
        } When {
            get("course/${course.id}")
        } Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            body().asString().compareTo(existingCourseResponse)
        }
    }

    private fun `an existing course`() {
        courseRepository.saveCourse(CourseMother.random())
    }

    companion object {
        private val course = CourseMother.random()
        private val existingCourseResponse = """
            {
              "id": "${course.id}"
              "name": "${course.name}"
              "created_at": "${course.createdAt}"
            }
        """.trimIndent()
    }
}
