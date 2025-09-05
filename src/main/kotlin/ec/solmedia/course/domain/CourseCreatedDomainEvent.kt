package ec.solmedia.course.domain

import ec.solmedia.shared.domain.DomainEvent
import java.time.LocalDateTime

class CourseCreatedDomainEvent(
    courseId: CourseId,
    courseName: CourseName,
    createdAt: LocalDateTime,
) : DomainEvent(
    EVENT_NAME,
    courseId.value.toString(),
    """
    {
        "id": "${courseId.value}",
        "name": "${courseName.value}",
        "createdAt": "$createdAt."
    }
    """.trimIndent(),
) {

    companion object {
        const val EVENT_NAME = "course.created"
    }
}
