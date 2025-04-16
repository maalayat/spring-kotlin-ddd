package ec.solmedia.course_counter.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.course_counter.domain.CoursesCounter
import ec.solmedia.course_counter.domain.CoursesCounterRepository
import java.util.*

class CoursesCounterIncrementer(private val repository: CoursesCounterRepository) {

    context(Raise<InvalidCourseId>)
    fun increment(courseId: CourseId) {
        val coursesCounter = repository.search() ?: CoursesCounter.initialize(
            UUID.randomUUID().toString())

        if (coursesCounter.hasNotIncremented(courseId)) {
            coursesCounter.increment(courseId)
            repository.save(coursesCounter)
        }
    }
}