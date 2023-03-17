package ec.solmedia.shared.config

import ec.solmedia.course.application.CourseCreator
import ec.solmedia.course.application.CourseFinder
import ec.solmedia.course.domain.CourseRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DependencyInjectionConf {

    @Bean
    fun courseCreator(courseRepository: CourseRepository) = CourseCreator(courseRepository)

    @Bean
    fun courseFinder(courseRepository: CourseRepository) = CourseFinder(courseRepository)
}
