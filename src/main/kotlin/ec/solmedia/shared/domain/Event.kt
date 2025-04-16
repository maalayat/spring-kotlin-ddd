package ec.solmedia.shared.domain

interface DomainEvent {
    fun eventName(): String
}

interface EventBus {
    fun publish(events: List<DomainEvent>)
}