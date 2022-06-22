package tech.harmonysoft.oss.test.clock

import tech.harmonysoft.oss.test.TestAware
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class TestClock : Clock(), TestAware {

    private val delegates = Stack<Clock>()
    private val _default = systemDefaultZone()
    private val lock = ReentrantLock()

    private val delegate: Clock
        get() = lock.withLock {
            delegates.takeIf { it.isNotEmpty() }?.peek() ?: _default
        }

    override fun withZone(zone: ZoneId): Clock {
        return lock.withLock {
            delegate.withZone(zone).apply {
                delegates.push(this)
            }
        }
    }

    override fun getZone(): ZoneId = delegate.zone
    override fun instant(): Instant  = delegate.instant()

    fun withInstant(millis: Long) {
        lock.withLock {
            delegates.push(fixed(Instant.ofEpochMilli(millis), zone))
        }
    }

    override fun onTestEnd() {
        lock.withLock {
            delegates.clear()
        }
    }
}