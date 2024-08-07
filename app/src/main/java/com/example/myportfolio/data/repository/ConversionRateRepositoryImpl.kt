package com.example.myportfolio.data.repository

import com.example.myportfolio.data.datasource.ConversionRateDataSource
import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject

class ConversionRateRepositoryImpl @Inject constructor(
    private val dataSource: ConversionRateDataSource
) : ConversionRateRepository {
    override suspend fun getConversionRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: Period
    ): List<ConversionRate> {
        return dataSource.getRates(sourceCurrency, targetCurrency, period)
    }
}
