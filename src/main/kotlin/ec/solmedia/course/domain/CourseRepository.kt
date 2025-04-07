package ec.solmedia.course.domain

import arrow.core.raise.Raise

interface CourseRepository {
    fun save(course: Course)

    context(Raise<CourseNotFound>)
    fun find(id: CourseId): Course
}
