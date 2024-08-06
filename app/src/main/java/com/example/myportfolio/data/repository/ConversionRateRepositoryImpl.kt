package com.example.myportfolio.data.repository

import com.example.myportfolio.data.datasource.ConversionRateDataSource
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.ConversionRateRepository
import javax.inject.Inject

class ConversionRateRepositoryImpl @Inject constructor(
    private val dataSource: ConversionRateDataSource
) : ConversionRateRepository {
    override suspend fun getConversionRate(currency: CurrencyCode): List<ConversionRate> {
        return when (currency) {
            CurrencyCode.USD -> dataSource.getRatesUSD()
            CurrencyCode.EUR -> dataSource.getRatesEUR()
            CurrencyCode.GBP -> dataSource.getRatesGBP()
            CurrencyCode.CHF -> dataSource.getRatesCHF()
            else -> throw IllegalArgumentException("Unsupported currency $currency.")
        }
    }
}
