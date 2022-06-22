package tech.harmonysoft.oss.test.event.bus

import com.google.common.eventbus.EventBus
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import tech.harmonysoft.oss.common.util.mapFirstNotNull

@Primary
@Component
class TestEventBus(
    private val strategies: Collection<EventProcessingStrategy>
) : EventBus("test") {

    private val sync = EventBus("test-sync-bus")
    private val async = EventBus("test-async-bus")

    override fun register(subscriber: Any) {
        sync.register(subscriber)
        async.register(subscriber)
    }

    override fun unregister(subscriber: Any) {
        sync.unregister(subscriber)
        async.unregister(subscriber)
    }

    override fun post(event: Any) {
        val processingType = strategies.mapFirstNotNull {
            it.decide(event).takeIf { decision ->
                decision != EventProcessingType.NEUTRAL
            }
        } ?: EventProcessingType.ASYNC

        when (processingType) {
            EventProcessingType.SYNC -> sync.post(event)
            EventProcessingType.ASYNC, EventProcessingType.NEUTRAL -> async.post(event)
        }
    }
}