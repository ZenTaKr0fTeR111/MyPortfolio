package com.example.myportfolio.data.repository

import com.example.myportfolio.data.datasource.conversion_rate.ConversionRateDataSource
import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionRateRepositoryImpl @Inject constructor(
    private val dataSource: ConversionRateDataSource
) : ConversionRateRepository {
    override suspend fun getConversionRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: Period
    ): List<ConversionRate> {
        return withContext(Dispatchers.IO) {
            dataSource.getRates(sourceCurrency, targetCurrency, period)
        }
    }
}
