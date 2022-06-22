package tech.harmonysoft.oss.test.time

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import tech.harmonysoft.oss.common.time.ClockProvider
import tech.harmonysoft.oss.test.TestAware
import java.time.Clock
import java.time.ZoneId

@Primary
@Component
class TestClockProvider(
    private val clock: TestClock
) : ClockProvider, TestAware {

    override fun getData(): Clock {
        return clock
    }

    override fun refresh() {
    }

    override fun probe(): Clock {
        return data
    }

    override fun withZone(zone: ZoneId): ClockProvider {
        return apply {
            clock.withZone(zone)
        }
    }
}