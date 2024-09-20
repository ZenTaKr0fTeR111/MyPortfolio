package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.TimePeriod
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject

class ConversionInteractor @Inject constructor(
    private val conversionRepository: ConversionRateRepository
) {
    suspend fun invokeFetchConversionRates(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: TimePeriod
    ): List<ConversionRate> {
        return conversionRepository.getConversionRate(sourceCurrency, targetCurrency, period)
    }

    suspend fun invokeScheduleDailyRatesFetching() =
        conversionRepository.scheduleDailyRatesFetching()
}
