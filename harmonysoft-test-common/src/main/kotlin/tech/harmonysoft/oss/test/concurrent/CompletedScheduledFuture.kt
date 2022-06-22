package tech.harmonysoft.oss.test.concurrent

import java.util.concurrent.Delayed
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class CompletedScheduledFuture<T>(
    private val result: T?
) : ScheduledFuture<T> {

    override fun compareTo(other: Delayed) = System.identityHashCode(this) - System.identityHashCode(other)

    override fun isDone() = true

    override fun get() = result

    override fun get(timeout: Long, unit: TimeUnit) = result

    override fun cancel(mayInterruptIfRunning: Boolean) = false

    override fun getDelay(unit: TimeUnit) = 0L

    override fun isCancelled() = false
}