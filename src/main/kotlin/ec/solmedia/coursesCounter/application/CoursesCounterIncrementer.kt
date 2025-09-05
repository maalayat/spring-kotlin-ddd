package ec.solmedia.coursesCounter.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.InvalidCourse
import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository

class CoursesCounterIncrementer(private val repository: CoursesCounterRepository) {

    context(Raise<InvalidCourse>)
    fun increment(courseId: CourseId) {
        val coursesCounter = repository.search() ?: CoursesCounter.initialize()
        if (!coursesCounter.hasIncremented(courseId)) {
            coursesCounter.increment(courseId)
            repository.save(coursesCounter)
        }
    }
}
