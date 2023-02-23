package ec.solmedia.course.application

import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.InvalidCourseId
import ec.solmedia.course.domain.InvalidCourseName
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CourseCreatorTest {

    @MockK(relaxUnitFun = true)
    private lateinit var repository: CourseRepository

    @InjectMockKs
    private lateinit var courseCreator: CourseCreator

    companion object {
        private const val id = "caebae03-3ee9-4aef-b041-21a400fa1bb7"
        private const val name = "Kotlin Hexagonal Architecture Api Course"
    }

    @Test
    fun `should create a course successfully`() {
        courseCreator.create(id, name)

        verify(exactly = 1) { repository.saveCourse(any()) }
    }

    @Test
    fun `should not create a course with invalid id`() {
        assertThrows<InvalidCourseId> { courseCreator.create("", name) }

        verify(exactly = 0) { repository.saveCourse(any()) }
    }

    @Test
    fun `should not create a course with invalid nane`() {
        assertThrows<InvalidCourseName> { courseCreator.create(id, "   ") }

        verify(exactly = 0) { repository.saveCourse(any()) }
    }
}
