package com.example.myportfolio.data.datasource.conversion_rate.local

import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.data.db.conversion_rates.ConversionRateEntity
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import javax.inject.Inject

class ConversionRateDbSource @Inject constructor(
    private val conversionRateDao: ConversionRateDao
) : ConversionRateLocalDataSource {
    override suspend fun getRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        return if (sourceCurrency == CurrencyCode.BYN) {
            conversionRateDao.getMostRecentEntries(days)
                .mapToDomainRates(true)
        } else {
            conversionRateDao.getRatesForCurrency(sourceCurrency, days)
                .mapToDomainRates(false)
        }
    }

    override suspend fun saveRates(newRates: List<ConversionRate>) {
        conversionRateDao.insertRates(newRates.mapToDbRates())
    }

    private fun List<ConversionRateEntity>.mapToDomainRates(isBYN: Boolean): List<ConversionRate> {
        return this.map { conversionRateEntity ->
            ConversionRate(
                if (isBYN) CurrencyCode.BYN else conversionRateEntity.sourceCurrency,
                CurrencyCode.BYN,
                if (isBYN) 1.00 else conversionRateEntity.rate,
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
