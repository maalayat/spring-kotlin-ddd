package ec.solmedia.acceptance

import ec.solmedia.shared.BaseRestAssuredTest
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
                      "id": "97fa5af4-bd81-45d5-974f-d5a3970af252",
                      "name": "Test Acceptance"
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
