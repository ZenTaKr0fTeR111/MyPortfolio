package com.example.myportfolio.domain.models

import java.time.LocalDate

data class ConversionRate(
    val sourceCurrency: CurrencyCode,
    val targetCurrency: CurrencyCode,
    val rate: Double,
    val date: LocalDate
)
