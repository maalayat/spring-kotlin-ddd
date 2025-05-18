package ec.solmedia.course.domain

import ec.solmedia.shared.domain.DomainEvent

private const val COURSE_CREATED_EVENT_NAME = "course.created"

class CourseCreatedDomainEvent(val aggregateId: String) :
    DomainEvent(COURSE_CREATED_EVENT_NAME, aggregateId)