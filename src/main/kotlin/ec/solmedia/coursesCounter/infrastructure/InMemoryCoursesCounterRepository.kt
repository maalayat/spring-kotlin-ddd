package ec.solmedia.coursesCounter.infrastructure

import arrow.core.raise.Raise
import ec.solmedia.course.domain.InvalidCourse
import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository

class InMemoryCoursesCounterRepository : CoursesCounterRepository {
    var coursesCounter: CoursesCounter? = null

    context(Raise<InvalidCourse>)
    override fun search(): CoursesCounter? = coursesCounter

    override fun save(counter: CoursesCounter) {
        coursesCounter = counter
    }
}
