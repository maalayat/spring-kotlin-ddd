package ec.solmedia.coursesCounter.application

import ec.solmedia.course.domain.CourseCreatedDomainEvent
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class IncrementCoursesCounterOnCourseCreated {
    private val log = LoggerFactory.getLogger(IncrementCoursesCounterOnCourseCreated::class.java)

    @EventListener
    fun handle(event: CourseCreatedDomainEvent) {
        log.info("IncrementCoursesCounterOnCourseCreated.handle() - received event type={}, name={}", event::class.simpleName, event.eventName)
        // TODO: Implement counter increment
    }
}
