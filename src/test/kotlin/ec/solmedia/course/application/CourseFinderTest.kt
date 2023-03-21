package ec.solmedia.course.application

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import ec.solmedia.course.domain.CourNameMother
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseError
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
        every { courseRepository::find.invoke(any(), any()) } returns CourseMother.fixed()
    }

    private fun `when the finder is executed`(): Either<CourseApplicationError, CourseResponse> {
        return either { courseFinder.find(CourseIdMother.id) }
    }

    private fun `then the found course is equals to expected`(actualCourse: Either<CourseApplicationError, CourseResponse>) {
        val expected = Either.Right(
            CourseResponse(
                id = CourseIdMother().value.toString(),
                name = CourNameMother().value,
                createdAt = DateMother(),
            ),
        )
        assertEquals(expected, actualCourse)
    }

    @Test
    fun `should throw an exception when course is not found`() {
        `given a non existent course`()

        val actualResult = `when the finder is executed with nonexistent course Id`()

        `then the result is a failure with no found exception`(actualResult)
    }

    private fun `given a non existent course`() {
        every { courseRepository::find.invoke(any(), any()) } answers {
            firstArg<Raise<CourseError>>().raise(CourseNotFound(nonexistentCourseId))
        }
    }

    private fun `when the finder is executed with nonexistent course Id`(): Either<CourseApplicationError, CourseResponse> {
        return either { courseFinder.find(otherUuid) }
    }

    private fun `then the result is a failure with no found exception`(actualResult: Either<CourseApplicationError, CourseResponse>) {
        val expected = either {
            raise(CourseNotFound(nonexistentCourseId))
        }
        assertEquals(expected, actualResult)
    }

    companion object {
        private const val otherUuid = "26ee3399-26b3-4f16-91a5-cb62951de5e2"
        private val nonexistentCourseId = CourseIdMother(otherUuid)
    }
}
