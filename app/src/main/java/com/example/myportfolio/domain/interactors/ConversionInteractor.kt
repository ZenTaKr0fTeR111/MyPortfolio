package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject

class ConversionInteractor @Inject constructor(
    private val conversionRepository: ConversionRateRepository
) {
    enum class Period {
        WEEK,
        MONTH,
        YEAR;
    }

    suspend fun invokeFetchConversionRates(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: Period
    ): List<ConversionRate> {
        return conversionRepository.getConversionRate(sourceCurrency, targetCurrency, period)
    }
}
