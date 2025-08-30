package ec.solmedia.shared.domain

interface EventBus {
    fun publish(events: List<DomainEvent>)
}
