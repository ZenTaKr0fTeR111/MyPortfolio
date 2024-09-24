package com.example.myportfolio.domain.repository

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.TimePeriod

interface ConversionRateRepository {
    suspend fun getConversionRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: TimePeriod
    ): List<ConversionRate>

    suspend fun scheduleDailyRatesFetching()

    suspend fun syncDailyRates()
}
