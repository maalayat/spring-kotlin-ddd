package ec.solmedia.course.application

import arrow.core.raise.Raise
import ec.solmedia.course.domain.Course
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseName
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.InvalidCourse
import ec.solmedia.shared.domain.EventBus

class CourseCreator(private val repository: CourseRepository, private val eventBus: EventBus) {

    context(Raise<InvalidCourse>)
    fun create(id: String, name: String) {
        Course(CourseId(id), CourseName(name)).let {
            repository.save(it)
            eventBus.publish(it.pullDomainEvents())
        }
    }
}
