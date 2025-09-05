package ec.solmedia.shared.config

import ec.solmedia.course.application.CourseCreator
import ec.solmedia.course.application.CourseFinder
import ec.solmedia.course.domain.CourseRepository
import ec.solmedia.coursesCounter.application.CoursesCounterIncrementer
import ec.solmedia.coursesCounter.application.IncrementCoursesCounterOnCourseCreated
import ec.solmedia.coursesCounter.domain.CoursesCounterRepository
import ec.solmedia.shared.domain.EventBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DependencyInjectionConf {

    @Bean
    fun courseCreator(courseRepository: CourseRepository, eventBus: EventBus) =
        CourseCreator(courseRepository, eventBus)

    @Bean
    fun courseFinder(courseRepository: CourseRepository) = CourseFinder(courseRepository)

    @Bean
    fun coursesCounterIncrementer(coursesCounterRepository: CoursesCounterRepository) =
        CoursesCounterIncrementer(coursesCounterRepository)

    @Bean
    fun incrementCoursesCounterOnCourseCreated(coursesCounterIncrementer: CoursesCounterIncrementer) =
        IncrementCoursesCounterOnCourseCreated(coursesCounterIncrementer)
}
