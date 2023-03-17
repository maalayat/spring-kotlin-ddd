package ec.solmedia.course.infrastructure.rest

import arrow.core.identity
import arrow.core.raise.Raise
import arrow.core.raise.eagerEffect
import arrow.core.raise.fold
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.course.domain.InvalidCourseName
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

internal fun <T> respond(f: Raise<CourseApplicationError>.() -> ResponseEntity<T>): ResponseEntity<T> =
    eagerEffect { f() }.fold(::handleThrowable, ::handleCourseError, ::identity)

private fun <T> handleCourseError(error: CourseApplicationError): ResponseEntity<T> =
    when (error) {
        is InvalidCourseId -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        is InvalidCourseName -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        is CourseNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

private fun <T> handleThrowable(throwable: Throwable): ResponseEntity<T> =
    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
