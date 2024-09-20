package com.example.myportfolio.ui.compose

import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.Stock
import com.example.myportfolio.domain.models.currencies
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock
import com.github.mikephil.charting.data.Entry
import java.time.LocalDate

val previewCurrency = UICurrency(
    domainAsset = Currency(
        431,
        "United States Dollar",
        CurrencyCode.USD,
        "$",
        100
    ),
    displayCurrency = currencies.getValue(CurrencyCode.USD),
    conversionRates = listOf(Entry(1f, 1f))
)

val previewStock = UIStock(
    domainAsset = Stock(
        1,
        "Nvidia Corporation",
        13491,
        currencies.getValue(CurrencyCode.USD),
        "NVDA"
    ),
    displayCurrency = currencies.getValue(CurrencyCode.USD),
    conversionRates = null
)

val previewBond = UIBond(
    domainAsset = Bond(
        4,
        "United States Department of the Treasury",
        100000,
        currencies.getValue(CurrencyCode.USD),
        "US10Y",
        1.59,
        LocalDate.of(2020, 12, 1),
        10
    ),
    displayCurrency = currencies.getValue(CurrencyCode.USD),
    conversionRates = null
)
