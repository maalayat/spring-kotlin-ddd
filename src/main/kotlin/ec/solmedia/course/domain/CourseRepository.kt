package ec.solmedia.course.domain

import arrow.core.raise.Raise

interface CourseRepository {
    fun save(course: Course)

    context(Raise<CourseApplicationError>)
    fun find(id: CourseId): Course
}
