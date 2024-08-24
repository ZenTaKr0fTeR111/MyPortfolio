package com.example.myportfolio.di

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myportfolio.data.DAILY_RATES_RETRY_WAIT_MINUTES
import com.example.myportfolio.data.remote.DailyRatesWorker
import com.example.myportfolio.getMillisTillMidnight
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object BackgroundModule {
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideWorkManagerConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    @Provides
    fun provideWorkRequest(constraints: Constraints): PeriodicWorkRequest {
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
