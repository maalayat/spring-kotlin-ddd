package ec.solmedia.coursesCounter.domain

import ec.solmedia.course.domain.CourseId
import ec.solmedia.shared.UuidMother

object CoursesCounterIdMother {
    operator fun invoke(): CoursesCounterId = CoursesCounterId(UuidMother.random())
}

object CoursesCounterTotalMother {
    operator fun invoke(): CoursesCounterTotal = CoursesCounterTotal.initialize()
}

object CoursesCounterMother {
    fun random(existingCourses: MutableList<CourseId> = mutableListOf()): CoursesCounter {
        return CoursesCounter(
            CoursesCounterIdMother(),
            CoursesCounterTotalMother(),
            existingCourses,
        )
    }
}
