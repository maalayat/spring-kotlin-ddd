package ec.solmedia.course_counter.domain

interface CoursesCounterRepository {
    fun save(coursesCounter: CoursesCounter)

    fun search(): CoursesCounter?
}