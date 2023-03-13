package ec.solmedia.course.infrastructure.acceptance

import ec.solmedia.course.infrastructure.shared.BaseRestAssuredTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class PostCreateCourseRestAssuredAcceptanceTest : BaseRestAssuredTest() {

    @Test
    fun `should create a course successfully`() {
        Given {
            contentType(ContentType.JSON)
            body(
                """
                    {
                      "id": "33048065-eb78-4738-8e23-0f35ab803e90",
                      "name": "Test Acceptance with Rest Assured"
                    }
                """.trimIndent(),
            )
        } When {
            post("/course")
        } Then {
            statusCode(HttpStatus.CREATED.value())
        }
    }
}
