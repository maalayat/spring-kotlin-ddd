package ec.solmedia.coursesCounter.domain

import ec.solmedia.course.domain.CourseId
import java.time.LocalDateTime
import java.util.*

@JvmInline
value class CoursesCounterId(val value: String) {
    init {
        require(value.isUuid()) { "CoursesCounterId is not a valid UUID." }
    }

    fun String.isUuid() = try {
        UUID.fromString(this)
        true
    } catch (_: IllegalArgumentException) {
        false
    }
}

@JvmInline
value class CoursesCounterTotal(val value: Int) {

    companion object {
        fun initialize(): CoursesCounterTotal = CoursesCounterTotal(0)
    }

    fun increment(): CoursesCounterTotal = CoursesCounterTotal(value + 1)
}

data class CoursesCounter(
    val id: CoursesCounterId,
    var total: CoursesCounterTotal,
    val existingCourses: MutableList<CourseId> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    fun hasIncremented(courseId: CourseId) = existingCourses.any { it.value == courseId.value }

    fun increment(courseId: CourseId) {
        total = total.increment()
        existingCourses.add(courseId)
    }

    companion object {
        fun initialize() = CoursesCounter(
            CoursesCounterId(UUID.randomUUID().toString()),
            CoursesCounterTotal.initialize(),
        )
    }
}
