package ec.solmedia.course.domain

sealed class CourseApplicationError

sealed class InvalidCourse : CourseApplicationError()
data class InvalidCourseId(val id: String, val cause: Throwable?) : InvalidCourse()
data class InvalidCourseName(val name: String, val cause: Throwable?) : InvalidCourse()
