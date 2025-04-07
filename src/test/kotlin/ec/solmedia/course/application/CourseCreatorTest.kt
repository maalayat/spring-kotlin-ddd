package ec.solmedia.course.application

import arrow.core.raise.either
import ec.solmedia.course.domain.CourNameMother
import ec.solmedia.course.domain.CourseIdMother
import ec.solmedia.course.domain.CourseRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class CourseCreatorTest {

    @MockK(relaxUnitFun = true)
    private lateinit var repository: CourseRepository

    @InjectMockKs
    private lateinit var courseCreator: CourseCreator

    @Test
    fun `should create a course successfully`() {
        either { courseCreator.create(CourseIdMother.id, CourNameMother.name) }

        verify(exactly = 1) { repository.save(any()) }
    }

    @Test
    fun `should not create a course with invalid id`() {
        assertTrue { either { courseCreator.create("Invalid", CourNameMother.name) }.isLeft() }
    }

    @Test
    fun `should not create a course with invalid nane`() {
        assertTrue { either { courseCreator.create(CourseIdMother.id, "   ") }.isLeft() }
    }
}
