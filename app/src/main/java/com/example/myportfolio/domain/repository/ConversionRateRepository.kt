package com.example.myportfolio.domain.repository

import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode

interface ConversionRateRepository {
    suspend fun getConversionRate(currency: CurrencyCode): List<ConversionRate>
}
