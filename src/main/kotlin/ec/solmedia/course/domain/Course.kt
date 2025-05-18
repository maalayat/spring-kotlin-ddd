package ec.solmedia.course.domain

import arrow.core.raise.Raise
import arrow.core.raise.ensure
import com.fasterxml.jackson.annotation.JsonProperty
import ec.solmedia.shared.domain.AggregateRoot
import ec.solmedia.shared.domain.Identifier
import ec.solmedia.shared.domain.StringValueObject
import java.time.LocalDateTime


data class CourseId private constructor(@JsonProperty("value") val value: String) :
    Identifier(value) {

    companion object {
        context(Raise<InvalidCourseId>)
        operator fun invoke(id: String): CourseId = try {
            CourseId(id)
        } catch (exception: IllegalArgumentException) {
            raise(InvalidCourseId(id, exception))
        }
    }
}

data class CourseName private constructor(val value: String) : StringValueObject(value) {

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
) : AggregateRoot() {
    init {
        record(CourseCreatedDomainEvent(id.value))
    }
}
