package ec.solmedia.course_counter.application

import ec.solmedia.course.domain.CourseId
import ec.solmedia.course_counter.domain.CoursesCounter
import ec.solmedia.course_counter.domain.CoursesCounterId
import ec.solmedia.course_counter.domain.CoursesCounterRepository

class CoursesCounterIncrementer(private val repository: CoursesCounterRepository) {

    fun increment(courseId: CourseId) {
        val coursesCounter = repository.search() ?: initializeCounter()

        if (coursesCounter.hasNotIncremented(courseId)) {
            coursesCounter.increment(courseId)
            repository.save(coursesCounter)
        }
    }

    private fun initializeCounter() = CoursesCounter.initialize(CoursesCounterId.random())
}