package ec.solmedia.coursesCounter.domain

interface CoursesCounterRepository {
    fun search(): CoursesCounter?

    fun save(counter: CoursesCounter)
}
