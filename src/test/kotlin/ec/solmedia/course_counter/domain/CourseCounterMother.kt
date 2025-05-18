package ec.solmedia.course_counter.domain

import ec.solmedia.course.domain.CourseIdMother

object CoursesCounterIdMother {
    operator fun invoke(): CoursesCounterId = CoursesCounterId.random()
}

object CoursesCounterTotalMother {
    operator fun invoke(): CoursesCounterTotal = CoursesCounterTotal.initialize()
}

object CourseCounterMother {
    fun random(): CoursesCounter = CoursesCounter(
        id = CoursesCounterIdMother(),
        total = CoursesCounterTotalMother(),
        existingCourses = mutableListOf(CourseIdMother()),
    )

}
