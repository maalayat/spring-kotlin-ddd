package ec.solmedia.course.domain

import arrow.core.raise.Raise

class CourseRepositoryMock : CourseRepository {
    override fun saveCourse(course: Course) {}

    context(Raise<CourseNotFound>)
    override fun find(id: CourseId): Course {
        if (id != CourseIdMother()) {
            raise(CourseNotFound(id))
        }
        return CourseMother.fixed()
    }
}
