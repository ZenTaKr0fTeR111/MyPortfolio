package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject

class ConversionInteractor @Inject constructor(
    private val conversionRepository: ConversionRateRepository
) {
    companion object {
        const val WEEK_DAYS = 7
        const val MONTH_DAYS = 30
        const val YEAR_DAYS = 365
    }

    suspend fun invokeFetchWeeklyRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode
    ): List<ConversionRate> {
        return invokeFetchFromRepository(sourceCurrency, targetCurrency, WEEK_DAYS)
    }

    suspend fun invokeFetchMonthlyRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode
    ): List<ConversionRate> {
        return invokeFetchFromRepository(sourceCurrency, targetCurrency, MONTH_DAYS)
    }

    suspend fun invokeFetchYearlyRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode
    ): List<ConversionRate> {
        return invokeFetchFromRepository(sourceCurrency, targetCurrency, YEAR_DAYS)
    }

    private suspend fun invokeFetchFromRepository(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        span: Int
    ): List<ConversionRate> {
        return if (sourceCurrency == CurrencyCode.BYN && targetCurrency == CurrencyCode.BYN) {
            conversionRepository.getConversionRate(CurrencyCode.USD).map {
                it.copy(sourceCurrency = CurrencyCode.BYN, rate = 1.00)
            }.take(span)
        } else if (targetCurrency == CurrencyCode.BYN) {
            conversionRepository.getConversionRate(sourceCurrency).take(span)
        } else if (sourceCurrency == CurrencyCode.BYN) {
            conversionRepository.getConversionRate(targetCurrency).map {
                it.copy(
                    sourceCurrency = sourceCurrency,
                    targetCurrency = targetCurrency,
                    rate = 1 / it.rate
                )
            }.take(span)
        } else {
            val sourceToByn = conversionRepository.getConversionRate(sourceCurrency)
            val targetToByn = conversionRepository.getConversionRate(targetCurrency)
            sourceToByn.zip(targetToByn) { srcToByn, trgToByn ->
                ConversionRate(
                    srcToByn.sourceCurrency,
                    trgToByn.sourceCurrency,
                    srcToByn.rate / trgToByn.rate,
                    srcToByn.date
                )
            }.take(span)
        }
    }
}
