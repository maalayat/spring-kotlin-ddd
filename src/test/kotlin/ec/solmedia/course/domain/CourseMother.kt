package ec.solmedia.course.domain

import ec.solmedia.shared.UuidMother
import ec.solmedia.shared.WordMother
import java.time.LocalDateTime

object DateMother {
    fun fixed(): LocalDateTime = LocalDateTime.of(2023, 1, 1, 0, 0)
}

object CourseMother {
    fun random(): Course = Course.of(UuidMother.random(), WordMother.random(), DateMother.fixed())
}
