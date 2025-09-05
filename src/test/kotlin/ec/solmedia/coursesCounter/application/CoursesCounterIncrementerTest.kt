package ec.solmedia.coursesCounter.application

import arrow.core.raise.either
import ec.solmedia.course.domain.CourseIdMother
import ec.solmedia.coursesCounter.domain.CoursesCounterMother
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository
import io.mockk.Ordering
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CoursesCounterIncrementerTest {

    @MockK(relaxUnitFun = true)
    private lateinit var repository: CoursesCounterRepository

    @InjectMockKs
    private lateinit var coursesCounterIncrementer: CoursesCounterIncrementer

    @Test
    fun `should increment the counter when the course is created`() {
        every { repository::search.invoke(any()) } returns CoursesCounterMother.random()

        either { coursesCounterIncrementer.increment(CourseIdMother()) }

        verify(ordering = Ordering.ORDERED) {
            repository::search.invoke(any())
            repository.save(any())
        }
    }

    @Test
    fun `should not increment the counter when the course is already incremented`() {
        every { repository::search.invoke(any()) } returns CoursesCounterMother.random(mutableListOf(CourseIdMother()))

        either { coursesCounterIncrementer.increment(CourseIdMother()) }

        verify(exactly = 1) { repository::search.invoke(any()) }
        verify(exactly = 0) { repository.save(any()) }
    }
}
