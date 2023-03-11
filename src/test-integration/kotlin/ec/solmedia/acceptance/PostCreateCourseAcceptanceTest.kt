package ec.solmedia.acceptance

import ec.solmedia.shared.BaseAcceptanceTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

class PostCreateCourseAcceptanceTest : BaseAcceptanceTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should create a course successfully`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
               {
                    "id": "97fa5af4-bd81-45d5-974f-d5a3970af252",
                    "name": "Test Acceptance"
               }     
                    """.trimIndent(),
                ),
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect {
                assertEquals("", it.response.contentAsString)
            }
    }
}
