package tech.harmonysoft.oss.test.event.bus

interface EventProcessingStrategy {

    fun decide(event: Any): EventProcessingType
}