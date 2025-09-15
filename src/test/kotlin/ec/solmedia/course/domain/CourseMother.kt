package ec.solmedia.course.domain

import ec.solmedia.shared.UuidMother
import ec.solmedia.shared.WordMother
import java.time.LocalDateTime

object CourseIdMother {
    const val id = "7ab75530-5da7-4b4a-b083-a779dd6c759e"
    operator fun invoke(uuid: String = id): CourseId = CourseId(uuid)
}

object CourNameMother {
    const val name = "Kotlin Hexagonal Architecture Api Course"
    operator fun invoke(): CourseName = CourseName(name)
}

object DateMother {
    operator fun invoke(): LocalDateTime = LocalDateTime.of(2023, 1, 1, 0, 0)
}

object CourseMother {

    fun random(
        id: String = UuidMother.random(),
        name: String = WordMother.random(),
        createdAt: LocalDateTime = DateMother(),
    ): Course = Course(
        id = CourseId(id),
        name = CourseName(name),
        createdAt = createdAt,
    )

    fun fixed(): Course = Course(
        id = CourseIdMother(),
        name = CourNameMother(),
        createdAt = DateMother(),
    )
}
