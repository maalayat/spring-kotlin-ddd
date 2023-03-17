package ec.solmedia.course.domain

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import java.time.LocalDateTime
import java.util.*

data class CourseId private constructor(val value: UUID) {

    companion object {
        context(Raise<InvalidCourseId>)
        operator fun invoke(id: String): CourseId = try {
            CourseId(UUID.fromString(id))
        } catch (exception: IllegalArgumentException) {
            raise(InvalidCourseId(id, exception))
        }
    }
}

data class CourseName private constructor(val value: String) {

    companion object {
        context(Raise<InvalidCourseName>)
        operator fun invoke(name: String): CourseName {
            ensure(name.isNotEmpty() && name.isNotBlank()) {
                InvalidCourseName(name, null)
            }

            return CourseName(name)
        }
    }
}

data class Course(
    val id: CourseId,
    val name: CourseName,
    val createdAt: LocalDateTime,
)
