package ec.solmedia.shared.domain

import java.time.LocalDateTime

abstract class DomainEvent(
    eventName: String,
    aggregateId: String,
    occurredOn: LocalDateTime = LocalDateTime.now()
)