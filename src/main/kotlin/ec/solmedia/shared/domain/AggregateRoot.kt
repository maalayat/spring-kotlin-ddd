package ec.solmedia.shared.domain

abstract class AggregateRoot {
    private var domainEvents = mutableListOf<DomainEvent>()

    fun pullDomainEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = domainEvents.slice(domainEvents.indices)
        domainEvents.clear()

        return events
    }

    protected fun record(domainEvent: DomainEvent) {
        domainEvents.add(domainEvent)
    }
}