package ec.solmedia.course.domain

import java.time.LocalDateTime
import java.util.*

data class CourseId private constructor(val value: UUID) {

    companion object {
        fun of(id: String) = try {
            CourseId(UUID.fromString(id))
        } catch (exception: Exception) {
            throw InvalidCourseId(id)
        }
    }
}

data class CourseName private constructor(val value: String) {

    companion object {
        fun of(name: String) = if (name.isEmpty() || name.isBlank()) {
            throw InvalidCourseName(name)
        } else {
            CourseName(name)
        }
    }
}

data class Course private constructor(
    val id: CourseId,
    val name: CourseName,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun of(id: String, name: String, createdAt: LocalDateTime) =
            Course(CourseId.of(id), CourseName.of(name), createdAt)
    }
}
