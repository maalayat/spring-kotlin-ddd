package ec.solmedia.course_counter.infrastructure.integration

import ec.solmedia.course.infrastructure.shared.BaseIntegrationTest
import ec.solmedia.course_counter.domain.CourseCounterMother
import ec.solmedia.course_counter.infrastructure.persistence.PostgreSQLCoursesCounterRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class PostgreSQLCoursesCounterRepositoryTest : BaseIntegrationTest() {

    @Autowired
    private lateinit var repository: PostgreSQLCoursesCounterRepository

    @Test
    fun `should save a course counter`() {
        val courseCounter = CourseCounterMother.random()
        repository.save(courseCounter)
        val courseCounterFromDb = repository.search()

        assertEquals(courseCounter, courseCounterFromDb)
    }
}