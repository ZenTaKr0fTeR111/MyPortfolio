package com.example.myportfolio.domain.models

import java.util.Date

data class ConversionRate(
    val sourceCurrency: Currency,
    val targetCurrency: Currency,
    val rate: Double,
    val date: Date
)
