package com.example.myportfolio.data.datasource

import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.Stock
import javax.inject.Inject

class AssetsSampleSource @Inject constructor() : AssetsDataSource {
    override fun getAssets() = assetsStorage

    override fun getAssetById(id: Int): Asset? {
        return assetsStorage.find { it.id == id }
    }

    override fun getBaseCurrency() = currencies.getValue(CurrencyCode.USD)

    val currencies = mutableListOf(
        Currency(100, "United States Dollar", CurrencyCode.USD, "$"),
        Currency(101, "Euro", CurrencyCode.EUR, "€"),
        Currency(102, "British Pound Sterling", CurrencyCode.GBP, "£"),
        Currency(103, "Swiss Franc", CurrencyCode.CHF, "₣"),
        Currency(104, "Belarusian Ruble", CurrencyCode.BYN, "Br")
    ).associateBy { it.code }

    val assetsStorage = mutableListOf(
        Stock(
            0, "Apple Inc.",
            23292,
            currencies.getValue(CurrencyCode.USD),
            "AAPL"
        ),
        Stock(
            1,
            "Nvidia Corporation",
            13491,
            currencies.getValue(CurrencyCode.USD),
            "NVDA"
        ),
        Stock(
            2,
            "Advanced Micro Devices, Inc.",
            18396,
            currencies.getValue(CurrencyCode.USD),
            "AMD"
        ),
        Stock(
            3,
            "Visa Inc.",
            26300,
            currencies.getValue(CurrencyCode.USD),
            "V"
        ),
        Bond(
            4,
            "United States Department of the Treasury",
            100000,
            currencies.getValue(CurrencyCode.USD),
            "US10Y",
            1.59
        )
    ) + currencies.values
}
