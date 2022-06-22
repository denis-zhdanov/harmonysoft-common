package tech.harmonysoft.oss.common.time

import tech.harmonysoft.oss.inpertio.client.ConfigProvider
import java.time.Clock
import java.time.ZoneId

interface ClockProvider : ConfigProvider<Clock> {

    fun withZone(zone: ZoneId): ClockProvider

    companion object {

        fun forZone(zoneId: ZoneId): ClockProvider {
            return ZonedSystemClockProvider(zoneId)
        }
    }
}