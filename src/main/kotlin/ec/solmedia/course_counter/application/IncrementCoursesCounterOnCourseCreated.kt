package ec.solmedia.course_counter.application

import arrow.core.raise.eagerEffect
import ec.solmedia.course.domain.CourseCreatedDomainEvent
import ec.solmedia.course.domain.CourseId
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class IncrementCoursesCounterOnCourseCreated(private val counter: CoursesCounterIncrementer) {

    @EventListener
    fun on(event: CourseCreatedDomainEvent) = eagerEffect {
        counter.increment(CourseId(event.aggregateId))
    }

}