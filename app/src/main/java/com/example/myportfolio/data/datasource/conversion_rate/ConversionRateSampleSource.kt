package com.example.myportfolio.data.datasource.conversion_rate

import com.example.myportfolio.data.datasource.conversion_rate.local.ConversionRateLocalDataSource
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import java.time.LocalDate
import javax.inject.Inject

class ConversionRateSampleSource @Inject constructor() : ConversionRateLocalDataSource {
    override suspend fun getRatesForCurrency(
        sourceCurrency: CurrencyCode,
        days: Int
    ): List<ConversionRate> {
        return when (sourceCurrency) {
            CurrencyCode.USD -> conversionRatesUSD.take(days)
            CurrencyCode.EUR -> conversionRatesEUR.take(days)
            CurrencyCode.GBP -> conversionRatesGBP.take(days)
            CurrencyCode.CHF -> conversionRatesCHF.take(days)
            CurrencyCode.BYN -> conversionRatesCHF.take(days).map {
                it.copy(sourceCurrency = CurrencyCode.BYN, rate = 1.00)
            }
        }
    }

    override suspend fun saveRates(newRates: List<ConversionRate>) {}

    private val conversionRatesUSD = listOf(
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1073,
            LocalDate.of(2024, 8, 11)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1073,
            LocalDate.of(2024, 8, 10)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1073,
            LocalDate.of(2024, 8, 9)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1007,
            LocalDate.of(2024, 8, 8)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.0919,
            LocalDate.of(2024, 8, 7)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.0916,
            LocalDate.of(2024, 8, 6)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1236,
            LocalDate.of(2024, 8, 5)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1253,
            LocalDate.of(2024, 8, 4)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1253,
            LocalDate.of(2024, 8, 3)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1253,
            LocalDate.of(2024, 8, 2)
        ),
        ConversionRate(
            CurrencyCode.USD,
            CurrencyCode.BYN,
            3.1277,
            LocalDate.of(2024, 8, 1)
        )
    )

    private val conversionRatesEUR = listOf(
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3935,
            LocalDate.of(2024, 8, 11)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3935,
            LocalDate.of(2024, 8, 10)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3935,
            LocalDate.of(2024, 8, 9)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3832,
            LocalDate.of(2024, 8, 8)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3740,
            LocalDate.of(2024, 8, 7)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3816,
            LocalDate.of(2024, 8, 6)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3794,
            LocalDate.of(2024, 8, 5)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3698,
            LocalDate.of(2024, 8, 4)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3698,
            LocalDate.of(2024, 8, 3)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3698,
            LocalDate.of(2024, 8, 2)
        ),
        ConversionRate(
            CurrencyCode.EUR,
            CurrencyCode.BYN,
            3.3877,
            LocalDate.of(2024, 8, 1)
        )
    )

    private val conversionRatesGBP = listOf(
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9355,
            LocalDate.of(2024, 8, 11)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9355,
            LocalDate.of(2024, 8, 10)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9355,
            LocalDate.of(2024, 8, 9)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9391,
            LocalDate.of(2024, 8, 8)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9300,
            LocalDate.of(2024, 8, 7)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9378,
            LocalDate.of(2024, 8, 6)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9789,
            LocalDate.of(2024, 8, 5)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9894,
            LocalDate.of(2024, 8, 4)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9894,
            LocalDate.of(2024, 8, 3)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            3.9894,
            LocalDate.of(2024, 8, 2)
        ),
        ConversionRate(
            CurrencyCode.GBP,
            CurrencyCode.BYN,
            4.0153,
            LocalDate.of(2024, 8, 1)
        )
    )

    private val conversionRatesCHF = listOf(
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.6228,
            LocalDate.of(2024, 8, 11)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.6228,
            LocalDate.of(2024, 8, 10)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.6228,
            LocalDate.of(2024, 8, 9)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.5923,
            LocalDate.of(2024, 8, 8)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.6199,
            LocalDate.of(2024, 8, 7)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.6417,
            LocalDate.of(2024, 8, 6)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.5895,
            LocalDate.of(2024, 8, 5)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.5646,
            LocalDate.of(2024, 8, 4)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.5646,
            LocalDate.of(2024, 8, 3)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.5646,
            LocalDate.of(2024, 8, 2)
        ),
        ConversionRate(
            CurrencyCode.CHF,
            CurrencyCode.BYN,
            3.552,
            LocalDate.of(2024, 8, 1)
        )
    )
}
