package com.example.myportfolio.data.repository

import com.example.myportfolio.data.datasource.conversion_rate.local.ConversionRateLocalDataSource
import com.example.myportfolio.data.datasource.conversion_rate.remote.ConversionRateRemoteDataSource
import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ConversionRateRepositoryImpl @Inject constructor(
    private val localDataSource: ConversionRateLocalDataSource,
    private val remoteDataSource: ConversionRateRemoteDataSource
) : ConversionRateRepository {
    override suspend fun getConversionRate(
        sourceCurrency: CurrencyCode,
        targetCurrency: CurrencyCode,
        period: Period
    ): List<ConversionRate> {
        return withContext(Dispatchers.IO) {
            val days = period.days
            val sourceCurrencyRates = async { getRatesForCurrency(sourceCurrency, days) }
            val targetCurrencyRates = async { getRatesForCurrency(targetCurrency, days) }

            sourceCurrencyRates.await().zip(targetCurrencyRates.await()) { source, target ->
                ConversionRate(
                    source.sourceCurrency,
                    target.sourceCurrency,
                    source.rate / target.rate,
                    source.date
                )
            }
        }
    }

    private suspend fun getRatesForCurrency(
        currency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        var sourceCurrencyRates = localDataSource.getRatesForCurrency(currency, days)

        return if (sourceCurrencyRates.isEmpty() || sourceCurrencyRates.size < days) {
            sourceCurrencyRates = remoteDataSource.loadRatesForCurrency(currency, days)
            localDataSource.saveRates(sourceCurrencyRates)
            sourceCurrencyRates
        } else {
            sourceCurrencyRates
        }
    }
}
