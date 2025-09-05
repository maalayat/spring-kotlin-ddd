package ec.solmedia.shared.domain

import java.time.LocalDateTime
import java.util.*

open class DomainEvent(val eventName: String, val aggregateId: String, val payload: String) {
    private val id = UUID.randomUUID()
    private val occurredOn = LocalDateTime.now()
}
