package ec.solmedia.course.application

import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseRepository
import java.time.LocalDateTime
import java.util.*

class CourseCreator(private val repository: CourseRepository) {

    fun create(id: String, name: String) {
        Course(UUID.fromString(id), name, LocalDateTime.now()).let {
            repository.saveCourse(it)
        }
    }
}
