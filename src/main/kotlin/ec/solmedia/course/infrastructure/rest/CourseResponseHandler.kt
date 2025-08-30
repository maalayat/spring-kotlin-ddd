package ec.solmedia.course.infrastructure.rest

import arrow.core.identity
import arrow.core.raise.Raise
import arrow.core.raise.eagerEffect
import arrow.core.raise.fold
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.course.domain.InvalidCourseName
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

private val log: Logger = LoggerFactory.getLogger("CourseResponseHandler")

internal fun <T> respond(f: Raise<CourseApplicationError>.() -> ResponseEntity<T>): ResponseEntity<T> {
    val result = eagerEffect { f() }.fold(
        { throwable ->
            log.error("respond() - handling throwable: ${throwable::class.simpleName}: ${throwable.message}")
            handleThrowable(throwable)
        },
        { error ->
            log.warn("respond() - handling domain error: ${error::class.simpleName}")
            handleCourseError(error)
        },
        { response ->
            log.info("respond() - success")
            identity(response)
        },
    )
    return result
}

private fun <T> handleCourseError(error: CourseApplicationError): ResponseEntity<T> = when (error) {
    is InvalidCourseId -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    is InvalidCourseName -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    is CourseNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
}

private fun <T> handleThrowable(throwable: Throwable): ResponseEntity<T> {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
}
