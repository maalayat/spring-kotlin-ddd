package ec.solmedia.coursesCounter.infrastructure.integration

import ec.solmedia.course.infrastructure.shared.BaseIntegrationTest
import ec.solmedia.coursesCounter.domain.CoursesCounter
import ec.solmedia.coursesCounter.domain.CoursesCounterMother
import ec.solmedia.coursesCounter.infrastructure.persistence.PostgreSQLCoursesCounterRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PostgreSQLCoursesCounterRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: PostgreSQLCoursesCounterRepository

    @Test
    fun `should save a counter`() {
        val counter = CoursesCounterMother.random()
        repository.save(counter)

        val courseCounterFromDb = repository.search() as CoursesCounter

        assertEquals(counter.id, courseCounterFromDb.id)
        assertEquals(counter.total, courseCounterFromDb.total)
        assertEquals(counter.existingCourses, courseCounterFromDb.existingCourses)
        assertNotNull(courseCounterFromDb.createdAt)
    }
}
