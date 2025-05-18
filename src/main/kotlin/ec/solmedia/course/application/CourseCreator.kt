package ec.solmedia.course.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.*
import ec.solmedia.shared.domain.EventBus
import java.time.LocalDateTime

class CourseCreator(private val repository: CourseRepository, private val eventBus: EventBus) {

    context(Raise<InvalidCourse>)
    fun create(id: String, name: String) {
        Course(CourseId(id), CourseName(name), LocalDateTime.now()).let {
            repository.save(it)
            eventBus.publish(it.pullDomainEvents())
        }
    }
}
