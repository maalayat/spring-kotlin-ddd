package ec.solmedia.course.domain

import java.time.LocalDateTime
import java.util.*

data class Course(
    val id: UUID,
    val name: String,
    val createdAt: LocalDateTime
)
