package ec.solmedia.coursesCounter.infrastructure

import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository

class InMemoryCoursesCounterRepository : CoursesCounterRepository {
    var coursesCounter: CoursesCounter? = null

    override fun search(): CoursesCounter? = coursesCounter

    override fun save(counter: CoursesCounter) {
        coursesCounter = counter
    }
}
