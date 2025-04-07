package ec.solmedia.course.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseName
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.InvalidCourse
import java.time.LocalDateTime

class CourseCreator(private val repository: CourseRepository) {

    context(Raise<InvalidCourse>)
    fun create(id: String, name: String) {
        Course(CourseId(id), CourseName(name), LocalDateTime.now()).let {
            repository.save(it)
        }
    }
}
