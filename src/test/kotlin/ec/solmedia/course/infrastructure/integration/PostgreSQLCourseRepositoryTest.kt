package ec.solmedia.course.infrastructure.integration

import arrow.core.Either
import arrow.core.raise.either
import ec.solmedia.course.domain.CourseId
import ec.solmedia.course.domain.CourseIdMother
import ec.solmedia.course.domain.CourseMother
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.infrastructure.persistence.PostgreSQLCourseRepository
import ec.solmedia.course.infrastructure.shared.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class PostgreSQLCourseRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: PostgreSQLCourseRepository

    @Test
    fun `should save a course`() {
        val courseId = "13590efb-c181-4c5f-9f95-b768abde13e2"
        val courseToSave = CourseMother.random(id = courseId)

        repository.save(courseToSave)
        val courseFromDb = either { repository.find(CourseId(courseId)) }

        assertEquals(Either.Right(courseToSave), courseFromDb)
    }

    @Test
    fun `should fail when course is not found`() {
        val courseId = CourseIdMother()
        val courseFromDb = either { repository.find(courseId) }

        assertEquals(Either.Left(CourseNotFound(courseId)), courseFromDb)
    }
}
