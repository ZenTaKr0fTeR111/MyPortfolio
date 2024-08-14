package com.example.myportfolio.data.datasource

import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode

interface ConversionRateDataSource {
    fun getRates(from: CurrencyCode, to: CurrencyCode, period: Period): List<ConversionRate>
}
