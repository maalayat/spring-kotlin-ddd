package ec.solmedia.shared.infrastructure

import ec.solmedia.shared.domain.DomainEvent
import ec.solmedia.shared.domain.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SpringApplicationEventBus(val publisher: ApplicationEventPublisher) : EventBus {
    override fun publish(events: List<DomainEvent>) {
        events.forEach(publisher::publishEvent)
    }
}