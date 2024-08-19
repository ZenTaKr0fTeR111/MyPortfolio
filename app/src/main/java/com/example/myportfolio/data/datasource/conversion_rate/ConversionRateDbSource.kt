package com.example.myportfolio.data.datasource.conversion_rate

import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.data.db.conversion_rates.ConversionRateEntity
import com.example.myportfolio.domain.interactors.ConversionInteractor
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionRateDbSource @Inject constructor(
    private val conversionRateDao: ConversionRateDao
) : ConversionRateDataSource {
    override suspend fun getRates(
        from: CurrencyCode,
        to: CurrencyCode,
        period: ConversionInteractor.Period
    ): List<ConversionRate> {
        val days = period.days
        return getRatesByCode(from, days).zip(getRatesByCode(to, days)) { source, target ->
            ConversionRate(
                source.sourceCurrency,
                target.sourceCurrency,
                source.rate / target.rate,
                source.date
            )
        }
    }

    private suspend fun getRatesByCode(code: CurrencyCode, days: Int): List<ConversionRate> {
        val rateEntities = withContext(Dispatchers.IO) {
            conversionRateDao.getRatesForCurrency(code, days)
        }
        return withContext(Dispatchers.Default) {
            rateEntities.mapToDomainRates()
        }
    }

    private fun List<ConversionRateEntity>.mapToDomainRates(): List<ConversionRate> {
        return this.map { conversionRateEntity ->
            ConversionRate(
                conversionRateEntity.sourceCurrency,
                CurrencyCode.BYN,
                conversionRateEntity.rate,
                conversionRateEntity.date
            )
        }
    }
}
