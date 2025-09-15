package ec.solmedia.course.domain

import ec.solmedia.shared.domain.DomainEvent
import java.time.LocalDateTime
import java.util.*

data class CourseId private constructor(val value: UUID) {

    companion object {
        operator fun invoke(id: String): CourseId = try {
            CourseId(UUID.fromString(id))
        } catch (exception: IllegalArgumentException) {
            throw InvalidCourseId(id, exception)
        }
    }
}

data class CourseName private constructor(val value: String) {

    companion object {
        operator fun invoke(name: String): CourseName {
            if (name.isEmpty() || name.isBlank()) {
                throw InvalidCourseName(name)
            }

            return CourseName(name)
        }
    }
}

data class Course(
    val id: CourseId,
    val name: CourseName,
    val createdAt: LocalDateTime,
    val domainEvents: MutableList<DomainEvent> = mutableListOf(),
) {
    companion object {
        operator fun invoke(id: CourseId, name: CourseName, createdAt: LocalDateTime = LocalDateTime.now()): Course {
            return Course(
                id,
                name,
                createdAt,
                mutableListOf(CourseCreatedDomainEvent(id, name, createdAt)),
            )
        }
    }

    fun pullDomainEvents(): MutableList<DomainEvent> {
        val events = domainEvents.toMutableList()
        domainEvents.clear()

        return events
    }
}
