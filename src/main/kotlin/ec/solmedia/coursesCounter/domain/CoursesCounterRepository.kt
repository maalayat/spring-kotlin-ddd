package ec.solmedia.coursesCounter.domain

import arrow.core.raise.Raise
import ec.solmedia.course.domain.InvalidCourse

interface CoursesCounterRepository {
    context(Raise<InvalidCourse>)
    fun search(): CoursesCounter?

    fun save(counter: CoursesCounter)
}
