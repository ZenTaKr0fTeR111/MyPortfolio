package com.example.myportfolio.data.datasource.conversion_rate.local

import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.data.db.conversion_rates.ConversionRateEntity
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionRateDbSource @Inject constructor(
    private val conversionRateDao: ConversionRateDao
) : ConversionRateLocalDataSource {
    override suspend fun getRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        return withContext(Dispatchers.IO) {
            if (sourceCurrency == CurrencyCode.BYN) {
                conversionRateDao.getRatesForCurrency(CurrencyCode.USD, days)
                    .mapToDomainRates(true)
            } else {
                conversionRateDao.getRatesForCurrency(sourceCurrency, days)
                    .mapToDomainRates(false)
            }
        }
    }

    override suspend fun saveRates(newRates: List<ConversionRate>) {
        val dbRates = withContext(Dispatchers.Default) {
            newRates.mapToDbRates()
        }
        withContext(Dispatchers.IO) {
            conversionRateDao.insertRates(dbRates)
        }
    }

    private fun List<ConversionRateEntity>.mapToDomainRates(isByn: Boolean): List<ConversionRate> {
        return this.map { conversionRateEntity ->
            ConversionRate(
                if (isByn) CurrencyCode.BYN else conversionRateEntity.sourceCurrency,
                CurrencyCode.BYN,
                if (isByn) 1.00 else conversionRateEntity.rate,
                conversionRateEntity.date
            )
        }
    }

    private fun List<ConversionRate>.mapToDbRates(): List<ConversionRateEntity> {
        return this.map { conversionRate ->
            ConversionRateEntity(
                sourceCurrency = conversionRate.sourceCurrency,
                rate = conversionRate.rate,
                date = conversionRate.date
            )
        }
    }
}
