package ec.solmedia.course.domain

sealed class CourseExceptions(message: String) : RuntimeException(message)

data class InvalidCourseId(val id: String) :
    CourseExceptions("The id <$id> is not a valid course id")

data class InvalidCourseName(val name: String) :
    CourseExceptions("The name <$name> is not a valida course name")
