package ec.solmedia.course.domain

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.identity
import arrow.core.raise.either
import ec.solmedia.shared.UuidMother
import ec.solmedia.shared.WordMother
import java.time.LocalDateTime

const val COURSE_ID = "7ab75530-5da7-4b4a-b083-a779dd6c759e"
private const val COURSE_NAME = "Course Finder Test Name"

object CourseIdMother {
    operator fun invoke(uuid: String = COURSE_ID): CourseId = either { CourseId(uuid) }.foldRight()
}

object CourNameMother {
    operator fun invoke(): CourseName = either { CourseName(COURSE_NAME) }.foldRight()
}

object DateMother {
    operator fun invoke(): LocalDateTime = LocalDateTime.of(2023, 1, 1, 0, 0)
}

object CourseMother {

    fun random(
        id: String = UuidMother.random(),
        name: String = WordMother.random(),
        createdAt: LocalDateTime = DateMother(),
    ): Course = either {
        Course(
            id = CourseId(id),
            name = CourseName(name),
            createdAt = createdAt,
        )
    }.getOrElse { fixed() }

    fun fixed(): Course = Course(
        id = CourseIdMother(),
        name = CourNameMother(),
        createdAt = DateMother(),
    )
}

fun <L, R> Either<L, R>.foldRight(): R {
    return fold({ throw RuntimeException() }, ::identity)
}
