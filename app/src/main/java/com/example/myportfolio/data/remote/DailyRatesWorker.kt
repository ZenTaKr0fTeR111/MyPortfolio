package com.example.myportfolio.data.remote

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myportfolio.domain.repository.ConversionRateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyRatesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val conversionRateRepository: ConversionRateRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        conversionRateRepository.loadDailyRates()
        return Result.success()
    }
}
