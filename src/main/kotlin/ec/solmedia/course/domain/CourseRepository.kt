package ec.solmedia.course.domain

interface CourseRepository {
    fun save(course: Course)

    fun find(id: CourseId): Course
}
