package com.example.myportfolio.data.datasource

import com.example.myportfolio.domain.models.ConversionRate

interface ConversionRateDataSource {
    fun getRatesUSD(): List<ConversionRate>

    fun getRatesEUR(): List<ConversionRate>

    fun getRatesGBP(): List<ConversionRate>

    fun getRatesCHF(): List<ConversionRate>
}
