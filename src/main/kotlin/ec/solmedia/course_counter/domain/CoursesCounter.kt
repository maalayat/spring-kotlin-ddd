package ec.solmedia.course_counter.domain

import ec.solmedia.course.domain.CourseId
import ec.solmedia.shared.domain.AggregateRoot
import ec.solmedia.shared.domain.Identifier
import ec.solmedia.shared.domain.IntValueObject
import java.util.*

data class CoursesCounterId(val value: String) : Identifier(value) {
    companion object {
        fun random() = CoursesCounterId(UUID.randomUUID().toString())
    }
}

data class CoursesCounterTotal(val value: Int) : IntValueObject(value) {
    companion object {
        fun initialize(): CoursesCounterTotal {
            return CoursesCounterTotal(1)
        }
    }

    fun increment(): CoursesCounterTotal {
        return CoursesCounterTotal(value() + 1)
    }
}

data class CoursesCounter(
    val id: CoursesCounterId,
    var total: CoursesCounterTotal,
    val existingCourses: MutableList<CourseId> = mutableListOf(),
) : AggregateRoot() {

    companion object {
        fun initialize(coursesCounterId: CoursesCounterId): CoursesCounter {
            return CoursesCounter(
                coursesCounterId,
                CoursesCounterTotal.initialize(),
            )
        }
    }

    fun hasNotIncremented(courseId: CourseId?): Boolean {
        return !existingCourses.contains(courseId)
    }

    fun increment(courseId: CourseId) {
        total = total.increment()
        existingCourses.add(courseId)
    }
}