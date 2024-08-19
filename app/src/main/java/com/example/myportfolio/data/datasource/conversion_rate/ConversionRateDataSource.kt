package com.example.myportfolio.data.datasource.conversion_rate

import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode

interface ConversionRateDataSource {
    suspend fun getRates(from: CurrencyCode, to: CurrencyCode, period: Period): List<ConversionRate>
}
