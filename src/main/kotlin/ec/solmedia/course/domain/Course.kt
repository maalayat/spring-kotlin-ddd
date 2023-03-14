package ec.solmedia.course.domain

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import java.time.LocalDateTime
import java.util.*

data class CourseId private constructor(val value: UUID) {

    companion object {
        context(Raise<InvalidCourseId>)
        fun of(id: String): CourseId = try {
            CourseId(UUID.fromString(id))
        } catch (exception: IllegalArgumentException) {
            raise(InvalidCourseId(id, exception))
        }
    }
}

data class CourseName private constructor(val value: String) {

    companion object {
        context(Raise<InvalidCourseName>)
        fun of(name: String): CourseName {
            ensure(name.isNotEmpty() && name.isNotBlank()) {
                InvalidCourseName(name, null)
            }

            return CourseName(name)
        }
    }
}

data class Course private constructor(
    val id: CourseId,
    val name: CourseName,
    val createdAt: LocalDateTime,
) {

    companion object {
        context(Raise<InvalidCourse>)
        fun of(id: String, name: String, createdAt: LocalDateTime) =
            Course(CourseId.of(id), CourseName.of(name), createdAt)
    }
}
