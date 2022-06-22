package tech.harmonysoft.oss.common.time

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.ZoneId

@Component
class ClockProviderImpl : ClockProvider {

    private val delegate: ClockProvider by lazy {
        val zone = System.getProperty(TIME_ZONE_KEY).takeUnless { it.isNullOrBlank() }?.let {
            ZoneId.of(it)
        } ?: ZoneId.systemDefault()
        ZonedSystemClockProvider(zone)
    }

    override fun getData(): Clock {
        return delegate.data
    }

    override fun withZone(zone: ZoneId): ClockProvider {
        return delegate.withZone(zone)
    }

    override fun refresh() {
    }

    override fun probe(): Clock {
        return data
    }

    override fun equals(other: Any?): Boolean {
        return delegate == (other as? ClockProviderImpl)?.delegate
    }

    override fun hashCode(): Int {
        return delegate.hashCode()
    }

    override fun toString(): String {
        return delegate.toString()
    }

    companion object {

        const val TIME_ZONE_KEY = "APP_TIME_ZONE"
    }
}