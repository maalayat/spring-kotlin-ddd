package ec.solmedia.course.domain

import arrow.core.identity
import arrow.core.raise.either
import ec.solmedia.shared.UuidMother
import ec.solmedia.shared.WordMother
import java.time.LocalDateTime

object DateMother {
    fun fixed(): LocalDateTime = LocalDateTime.of(2023, 1, 1, 0, 0)
}

object CourseMother {

    fun random(
        id: String = UuidMother.random(),
        name: String = WordMother.random(),
        date: LocalDateTime = DateMother.fixed(),
    ): Course = either {
        Course.of(
            id,
            name,
            date,
        )
    }.fold({ throw RuntimeException() }, ::identity)
}
