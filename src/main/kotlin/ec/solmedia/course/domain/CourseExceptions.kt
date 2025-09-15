package ec.solmedia.course.domain

sealed class CourseApplicationError(message: String? = null, cause: Throwable? = null) : Exception(message, cause)

sealed class InvalidCourse(message: String, cause: Throwable?) : CourseApplicationError(message, cause)
data class InvalidCourseId(val id: String, override val cause: Throwable?) : InvalidCourse("Invalid course ID: $id", cause)
data class InvalidCourseName(val name: String, override val cause: Throwable? = null) : InvalidCourse("Invalid course name: $name", cause)

sealed class CourseError : CourseApplicationError()
data class CourseNotFound(val courseId: CourseId) : CourseError()
