package ec.solmedia.course.domain

interface CourseRepository {
    fun saveCourse(course: Course)
}
