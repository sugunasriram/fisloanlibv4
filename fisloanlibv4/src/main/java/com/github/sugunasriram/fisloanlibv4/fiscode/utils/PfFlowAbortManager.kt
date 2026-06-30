package com.github.sugunasriram.fisloanlibv4.fiscode.utils

import kotlinx.coroutines.Job

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
}