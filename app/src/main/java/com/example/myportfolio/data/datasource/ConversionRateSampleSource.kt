package com.example.myportfolio.data.datasource

import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import java.time.LocalDate
import javax.inject.Inject

class ConversionRateSampleSource @Inject constructor() : ConversionRateDataSource {
    companion object {
        private const val WEEK = 7
        private const val MONTH = 30
        private const val YEAR = 365
    }

    override fun getRates(
        from: CurrencyCode,
        to: CurrencyCode,
        period: Period
    ): List<ConversionRate> {
        val days = when (period) {
            Period.WEEK -> WEEK
            Period.MONTH -> MONTH
            Period.YEAR -> YEAR
        }
        return getRatesByCode(from, days).zip(getRatesByCode(to, days)) { source, target ->
            ConversionRate(
                source.sourceCurrency,
                target.sourceCurrency,
                source.rate / target.rate,
                source.date
            )
        }
    }

    private fun getRatesByCode(code: CurrencyCode, days: Int): List<ConversionRate> {
        return when (code) {
            CurrencyCode.USD -> conversionRatesUSD.take(days)
            CurrencyCode.EUR -> conversionRatesEUR.take(days)
            CurrencyCode.GBP -> conversionRatesGBP.take(days)
            CurrencyCode.CHF -> conversionRatesCHF.take(days)
            CurrencyCode.BYN -> conversionRatesCHF.take(days).map {
                it.copy(sourceCurrency = CurrencyCode.BYN, rate = 1.00)
            }
        }
    }

    private val conversionRatesUSD = listOf(
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
