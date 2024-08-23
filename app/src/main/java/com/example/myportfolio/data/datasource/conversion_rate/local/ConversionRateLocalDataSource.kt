package com.example.myportfolio.data.datasource.conversion_rate.local

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode

interface ConversionRateLocalDataSource {
    suspend fun getRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate>

    suspend fun saveRates(newRates: List<ConversionRate>)
}
