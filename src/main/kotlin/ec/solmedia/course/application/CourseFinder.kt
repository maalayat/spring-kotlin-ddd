package ec.solmedia.course.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseRepository
import java.time.LocalDateTime

class CourseFinder(private val courseRepository: CourseRepository) {

    context(Raise<CourseApplicationError>)
    fun find(courseId: String): CourseResponse {
        val cid = CourseId(courseId)
        val course = courseRepository.find(cid)
        return CourseResponse(course)
    }
}

data class CourseResponse(val id: String, val name: String, val createdAt: LocalDateTime) {
    companion object {
        operator fun invoke(course: Course): CourseResponse = with(course) {
            CourseResponse(
                id = id.value.toString(),
                name = name.value,
                createdAt = createdAt,
            )
        }
    }
}
