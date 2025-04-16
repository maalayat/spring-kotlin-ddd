package ec.solmedia.course_counter.domain

import arrow.core.raise.Raise
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.shared.domain.Identifier
import ec.solmedia.shared.domain.IntValueObject

data class CoursesCounterId private constructor(val value: String) : Identifier(value) {
    companion object {
        context(Raise<InvalidCourseId>)
        operator fun invoke(value: String): CoursesCounterId = try {
            CoursesCounterId(value)
        } catch (exception: IllegalArgumentException) {
            raise(InvalidCourseId(value, exception))
        }
    }
}

data class CoursesCounterTotal private constructor(val value: Int) : IntValueObject(value) {
    companion object {
        operator fun invoke(value: Int): CoursesCounterTotal {
            return CoursesCounterTotal(value)
        }

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
    val existingCourses: MutableList<CourseId>,
) {

    companion object {
        context(Raise<InvalidCourseId>)
        fun initialize(id: String): CoursesCounter {
            return CoursesCounter(
                CoursesCounterId(id),
                CoursesCounterTotal.initialize(),
                mutableListOf()
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