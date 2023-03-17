package ec.solmedia.course.application

import arrow.core.Either
import arrow.core.raise.either
import ec.solmedia.course.domain.CourNameMother
import ec.solmedia.course.domain.CourseApplicationError
import ec.solmedia.course.domain.CourseIdMother
import ec.solmedia.course.domain.CourseNotFound
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.course.domain.CourseRepositoryMock
import ec.solmedia.course.domain.DateMother
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CourseFinderTest {

    private lateinit var courseRepository: CourseRepository
    private lateinit var courseFinder: CourseFinder

    @BeforeEach
    internal fun setUp() {
        courseRepository = CourseRepositoryMock()
        courseFinder = CourseFinder(courseRepository)
    }

    @Test
    fun `should find an existing course`() {
        val actualCourse = `when the finder is executed`()

        `then the found course is equals to expected`(actualCourse)
    }

    @Test
    fun `should throw an exception when course is not found`() {
        val actualResult = `when the finder is executed with nonexistent course Id`()

        `then the result is a failure with no found exception`(actualResult)
    }

    private fun `then the result is a failure with no found exception`(actualResult: Either<CourseApplicationError, CourseResponse>) {
        val expected = either {
            raise(CourseNotFound(nonexistentCourseId))
        }
        assertEquals(expected, actualResult)
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

    private fun `when the finder is executed with nonexistent course Id`(): Either<CourseApplicationError, CourseResponse> {
        return either { courseFinder.find(otherUuid) }
    }

    companion object {
        private const val otherUuid = "26ee3399-26b3-4f16-91a5-cb62951de5e2"
        private val nonexistentCourseId = CourseIdMother(otherUuid)
    }
}
