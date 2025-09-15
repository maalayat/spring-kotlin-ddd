package ec.solmedia.course.infrastructure.rest

import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.course.domain.InvalidCourseName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CourseResponseExceptionHandler : ResponseEntityExceptionHandler() {
    private val log: Logger = LoggerFactory.getLogger("CourseResponseExceptionHandler")

    @ExceptionHandler(InvalidCourseId::class)
    fun handle(error: InvalidCourseId) = handleError(error, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(InvalidCourseName::class)
    fun handle(error: InvalidCourseName) = handleError(error, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(CourseNotFound::class)
    fun handle(error: CourseNotFound) = handleError(error, HttpStatus.NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception) = handleError(exception, HttpStatus.INTERNAL_SERVER_ERROR)

    private fun handleError(error: Exception, status: HttpStatus): ProblemDetail {
        log.error("Exception Handler: ${error.message}")
        return ProblemDetail.forStatus(status)
    }
}
