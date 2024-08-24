package com.example.myportfolio.data.datasource.conversion_rate.remote

import com.example.myportfolio.Formatters
import com.example.myportfolio.data.remote.ConversionRateApiService
import com.example.myportfolio.data.remote.SerializableConversionRate
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.parseApiDate
import com.example.myportfolio.safeFormat
import java.time.LocalDate
import javax.inject.Inject

class ConversionRateApiDataSource @Inject constructor(
    private val apiService: ConversionRateApiService
) : ConversionRateRemoteDataSource {
    override suspend fun loadRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        return apiService.getRateDynamicByCurrencyId(
            sourceCurrency.id,
            LocalDate.now().minusDays(days.toLong() - 1)
                .safeFormat(Formatters.API_ARGS_FORMATTER),
            LocalDate.now().safeFormat(Formatters.API_ARGS_FORMATTER)
        ).mapToDomainRates()
    }

    override suspend fun retrieveDailyRates(): List<ConversionRate> {
        return apiService.getDailyRates().mapToDomainRates()
    }

    private fun List<SerializableConversionRate>.mapToDomainRates(): List<ConversionRate> {
        return this.mapNotNull { serializableConversionRate ->
            try {
                ConversionRate(
                    CurrencyCode.getById(serializableConversionRate.id),
                    CurrencyCode.BYN,
                    serializableConversionRate.rate,
                    serializableConversionRate.date.parseApiDate()
                )
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}
