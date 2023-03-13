package ec.solmedia.course.infrastructure.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetFindCourseController {

    @GetMapping("course/{id}")
    fun findCourse(@PathVariable id: String): ResponseEntity<String> {
        return ResponseEntity.ok(
            """
                {
                    "id": "f2fe1e4e-1e8f-493b-ac67-2c88090cae0a",
                    "name": "Saved course",
                    "created_at": "2022-08-31T09:07:36"
                }
            """.trimIndent(),
        )
    }
}
