package ec.solmedia.shared.infrastructure

import ec.solmedia.shared.domain.DomainEvent
import ec.solmedia.shared.domain.EventBus
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SpringApplicationEventBus(private val publisher: ApplicationEventPublisher) : EventBus {

    private val log = LoggerFactory.getLogger(SpringApplicationEventBus::class.java)

    override fun publish(events: List<DomainEvent>) {
        log.info("EventBus.publish() - publishing {} event(s)", events.size)
        events.forEach { event ->
            log.info(
                "EventBus.publish() - publishing event type={}, name={}",
                event::class.simpleName,
                event.eventName,
            )
            publisher.publishEvent(event)
        }
    }
}
