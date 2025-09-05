package ec.solmedia.coursesCounter.application

import arrow.core.identity
import arrow.core.raise.either
import ec.solmedia.course.domain.CourseCreatedDomainEvent
import ec.solmedia.course.domain.CourseId
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener

class IncrementCoursesCounterOnCourseCreated(private val coursesCounterIncrementer: CoursesCounterIncrementer) {
    private val log = LoggerFactory.getLogger(IncrementCoursesCounterOnCourseCreated::class.java)

    @EventListener
    fun handle(event: CourseCreatedDomainEvent) {
        log.info(
            "Received event type={}, name={}",
            event::class.simpleName,
            event.eventName,
        )
        val aggregateId = event.aggregateId
        val eitherCourseId = either { CourseId(aggregateId) }
        val courseId = eitherCourseId.fold(
            { log.error("Invalid course id={}", aggregateId); throw RuntimeException() },
            ::identity,
        )
        either { coursesCounterIncrementer.increment(courseId) }
    }
}
