package ec.solmedia.course.infrastructure.rest

import ec.solmedia.course.application.CourseCreator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class PostCreateCourseController(private val courseCreator: CourseCreator) {

    @PostMapping("/course")
    fun createCourse(@RequestBody request: CreateCourseRequest): ResponseEntity<String> {
        courseCreator.create(request.id, request.name)
        return ResponseEntity.created(URI.create("/course/${request.id}")).build()
    }
}

data class CreateCourseRequest(val id: String, val name: String)
