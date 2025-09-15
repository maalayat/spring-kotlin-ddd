package ec.solmedia.course.application

import ec.solmedia.course.domain.CourNameMother
import ec.solmedia.course.domain.CourseIdMother
import ec.solmedia.course.domain.CourseMother
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.DateMother
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CourseFinderTest {

    @MockK(relaxUnitFun = true)
    private lateinit var courseRepository: CourseRepository

    @InjectMockKs
    private lateinit var courseFinder: CourseFinder

    @Test
    fun `should find an existing course`() {
        `given a saved course`()

        val actualCourse = `when the finder is executed`()

        `then the found course is equals to expected`(actualCourse)
    }

    private fun `given a saved course`() {
        every { courseRepository.find(any()) } returns CourseMother.fixed()
    }

    private fun `when the finder is executed`() = courseFinder.find(CourseIdMother.id)

    private fun `then the found course is equals to expected`(actualCourse: CourseResponse) {
        val expected = CourseResponse(
            id = CourseIdMother().value.toString(),
            name = CourNameMother().value,
            createdAt = DateMother(),
        )

        assertEquals(expected, actualCourse)
    }

    @Test
    fun `should throw an exception when course is not found`() {
        `given a non existent course`()

        assertThrows<CourseNotFound> { `when the finder is executed`() }
    }

    private fun `given a non existent course`() {
        every { courseRepository.find(any()) } throws CourseNotFound(nonexistentCourseId)
    }

    companion object {
        private const val otherUuid = "26ee3399-26b3-4f16-91a5-cb62951de5e2"
        private val nonexistentCourseId = CourseIdMother(otherUuid)
    }
}
