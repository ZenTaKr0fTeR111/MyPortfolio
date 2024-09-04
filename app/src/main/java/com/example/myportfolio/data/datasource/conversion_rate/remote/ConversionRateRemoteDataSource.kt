package com.example.myportfolio.data.datasource.conversion_rate.remote

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode

interface ConversionRateRemoteDataSource {
    suspend fun loadRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate>

    suspend fun retrieveDailyRates(): List<ConversionRate>
}
