package tech.harmonysoft.oss.test.concurrent

import java.util.concurrent.*

class SameThreadScheduledExecutorService : ScheduledExecutorService {

    private val recursiveCall = ThreadLocal.withInitial { false }
    private var terminated = false

    private fun doExecute(action: Runnable): ScheduledFuture<*> {
        try {
            if (recursiveCall.get()) {
                return CompletedScheduledFuture(null)
            }
            recursiveCall.set(true)
            action.run()
            return CompletedScheduledFuture(null)
        } finally {
            recursiveCall.set(false)
        }
    }

    override fun schedule(command: Runnable, delay: Long, unit: TimeUnit): ScheduledFuture<*> {
        return doExecute(command)
    }

    override fun <V : Any?> schedule(callable: Callable<V>, delay: Long, unit: TimeUnit): ScheduledFuture<V> {
        return CompletedScheduledFuture(callable.call())
    }

    override fun <T : Any?> submit(task: Runnable, result: T): Future<T> {
        doExecute(task)
        return CompletedScheduledFuture(result)
    }

    override fun submit(task: Runnable): Future<*> {
        return doExecute(task)
    }

    override fun <T : Any?> invokeAny(tasks: MutableCollection<out Callable<T>>): T {
        for (task in tasks) {
            try {
                return task.call()
            } catch (ignore: Exception) {
            }
        }
        throw IllegalArgumentException("all given tasks failed to execute")
    }

    override fun <T : Any?> invokeAny(tasks: MutableCollection<out Callable<T>>, timeout: Long, unit: TimeUnit): T {
        return invokeAny(tasks)
    }

    override fun scheduleAtFixedRate(
        command: Runnable,
        initialDelay: Long,
        period: Long,
        unit: TimeUnit
    ): ScheduledFuture<*> {
        return doExecute(command)
    }
}