package ec.solmedia.coursesCounter.infrastructure.integration

import arrow.core.Either
import arrow.core.raise.either
import ec.solmedia.course.domain.InvalidCourse
import ec.solmedia.course.infrastructure.shared.BaseIntegrationTest
import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterMother
import ec.solmedia.coursesCounter.infrastructure.persistence.PostgreSQLCoursesCounterRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class PostgreSQLCoursesCounterRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: PostgreSQLCoursesCounterRepository

    @Test
    fun `should save a counter`() {
        val counter = CoursesCounterMother.random()
        repository.save(counter)

        val courseCounterFromDb: Either<InvalidCourse, CoursesCounter?> = either { repository.search() }

        assertIs<Either.Right<*>>(courseCounterFromDb)
        val retrievedCounter = courseCounterFromDb.value as CoursesCounter
        assertEquals(counter.id, retrievedCounter.id)
        assertEquals(counter.total, retrievedCounter.total)
        assertEquals(counter.existingCourses, retrievedCounter.existingCourses)
        assertNotNull(retrievedCounter.createdAt)
    }
}
