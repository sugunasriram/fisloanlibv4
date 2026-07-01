package com.github.sugunasriram.fisloanlibv4.fiscode.utils

import kotlinx.coroutines.Job
import java.util.concurrent.atomic.AtomicInteger

object PfFlowAbortManager {

    @Volatile
    var isAborted: Boolean = false
        private set

    private val activeJobs = mutableSetOf<Job>()

    fun reset() {
        isAborted = false
        activeJobs.clear()
    }

    fun track(job: Job): Job {
        activeJobs.add(job)

        job.invokeOnCompletion {
            activeJobs.remove(job)
        }

        return job
    }

    fun abort() {
        isAborted = true

        activeJobs.toList().forEach { job ->
            job.cancel()
        }

        activeJobs.clear()
    }

    private val flowCounter = AtomicInteger(0)

    fun startNewFlow(): Int {
        return flowCounter.incrementAndGet()
    }

    fun isStale(flowId: Int): Boolean {
        return flowId != flowCounter.get()
    }
}