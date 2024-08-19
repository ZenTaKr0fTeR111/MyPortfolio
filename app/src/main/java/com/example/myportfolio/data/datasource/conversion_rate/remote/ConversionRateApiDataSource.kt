package com.example.myportfolio.data.datasource.conversion_rate.remote

import com.example.myportfolio.FormatSubject
import com.example.myportfolio.data.remote.ConversionRateApiService
import com.example.myportfolio.data.remote.SerializableConversionRate
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.formatAppDetails
import com.example.myportfolio.parseApiDate
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionRateApiDataSource @Inject constructor(
    private val apiService: ConversionRateApiService
) : ConversionRateRemoteDataSource {
    override suspend fun loadRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        val rateEntities = withContext(Dispatchers.IO) {
            apiService.getRateDynamicByCurrencyId(
                sourceCurrency.id,
                LocalDate.now().minusDays(days.toLong() - 1)
                    .formatAppDetails(FormatSubject.API_ARGS),
                LocalDate.now().formatAppDetails(FormatSubject.API_ARGS)
            )
        }
        return withContext(Dispatchers.Default) {
            rateEntities.mapToDomainRates()
        }
    }

    private fun List<SerializableConversionRate>.mapToDomainRates(): List<ConversionRate> {
        return this.map { serializableConversionRate ->
            ConversionRate(
                CurrencyCode.getById(serializableConversionRate.id),
                CurrencyCode.BYN,
                serializableConversionRate.rate,
                serializableConversionRate.date.parseApiDate()
            )
        }
    }
}
