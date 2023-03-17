package ec.solmedia.course.infrastructure.rest

import ec.solmedia.course.application.CourseFinder
import ec.solmedia.course.application.CourseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetFindCourseController(private val courseFinder: CourseFinder) {

    @GetMapping("course/{id}")
    fun findCourse(@PathVariable id: String): ResponseEntity<CourseResponse> =
        respond {
            ResponseEntity.ok(courseFinder.find(id))
        }
}
