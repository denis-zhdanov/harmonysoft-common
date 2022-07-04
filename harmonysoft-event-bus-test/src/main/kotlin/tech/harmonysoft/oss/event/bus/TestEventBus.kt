package tech.harmonysoft.oss.event.bus

import tech.harmonysoft.oss.common.collection.mapFirstNotNull
import javax.annotation.Priority
import javax.inject.Named

@Priority(Int.MAX_VALUE)
@Named
class TestEventBus(
    private val strategies: Collection<EventProcessingStrategy>,
    factory: EventBusFactory
) : EventBus {

    private val sync = factory.sync()
    private val async = factory.async()

    override fun register(subscriber: Any) {
        sync.register(subscriber)
        async.register(subscriber)
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