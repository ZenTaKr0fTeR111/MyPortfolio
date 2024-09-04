package com.example.myportfolio.data.work_manager

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.example.myportfolio.data.DAILY_RATES_RETRY_WAIT_MINUTES
import com.example.myportfolio.getMillisTillMidnight
import java.util.concurrent.TimeUnit

object WorkRequests {
    fun getDailyWorkRequest(): PeriodicWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        return PeriodicWorkRequestBuilder<DailyRatesWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                DAILY_RATES_RETRY_WAIT_MINUTES,
                TimeUnit.MINUTES
            )
            .setInitialDelay(
                getMillisTillMidnight(),
                TimeUnit.MILLISECONDS
            )
            .build()
    }
}
