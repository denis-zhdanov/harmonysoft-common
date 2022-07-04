package tech.harmonysoft.oss.common.schedule.impl

import org.springframework.stereotype.Component
import tech.harmonysoft.oss.common.schedule.TaskScheduler
import tech.harmonysoft.oss.common.schedule.TaskSchedulerFactory
import tech.harmonysoft.oss.common.time.ClockProvider
import java.util.concurrent.ScheduledExecutorService

@Component
class TaskSchedulerFactoryImpl(
    private val clockProvider: ClockProvider,
    private val threadPool: ScheduledExecutorService
) : TaskSchedulerFactory {

    override fun newScheduler(schedulerId: String): TaskScheduler {
        return TaskSchedulerImpl(
            schedulerId = schedulerId,
            clockProvider = clockProvider,
            threadPool = threadPool
        )
    }
}