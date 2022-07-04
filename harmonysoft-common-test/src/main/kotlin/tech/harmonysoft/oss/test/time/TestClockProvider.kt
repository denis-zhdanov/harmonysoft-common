package tech.harmonysoft.oss.test.time

import tech.harmonysoft.oss.common.time.ClockProvider
import tech.harmonysoft.oss.test.TestAware
import java.time.Clock
import java.time.ZoneId
import javax.annotation.Priority
import javax.inject.Named

@Priority(Int.MAX_VALUE)
@Named
class TestClockProvider : ClockProvider, TestAware {

    private val clock = TestClock()

    override fun getData(): TestClock {
        return clock
    }

    override fun probe(): Clock {
        return data
    }

    override fun onTestStart() {
        clock.onTestEnd()
    }

    override fun onTestEnd() {
        clock.onTestEnd()
    }

    override fun withZone(zone: ZoneId): ClockProvider {
        return apply {
            clock.withZone(zone)
        }
    }

    override fun refresh() {
    }
}