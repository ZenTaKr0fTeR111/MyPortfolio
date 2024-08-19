package com.example.myportfolio.ui.models

import android.content.Context
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stock
import com.github.mikephil.charting.data.Entry

sealed class UIAsset(
    open val domainAsset: Asset,
    open val displayCurrency: Currency,
    open val conversionRates: List<Entry>?
) {
    abstract fun getPriceString(context: Context): String
}

data class UIStock(
    override val domainAsset: Stock,
    override val displayCurrency: Currency,
    override val conversionRates: List<Entry>?
) : UIAsset(domainAsset, displayCurrency, conversionRates) {
    override fun getPriceString(context: Context) = if (conversionRates.isNullOrEmpty()) {
        context.getString(
            R.string.price,
            domainAsset.getBasePrice(),
            domainAsset.baseCurrency.symbol
        )
    } else {
        context.getString(
            R.string.price,
            domainAsset.getBasePrice() * conversionRates.last().y,
            displayCurrency.symbol
        )
    }
}

data class UIBond(
    override val domainAsset: Bond,
    override val displayCurrency: Currency,
    override val conversionRates: List<Entry>?
) : UIAsset(domainAsset, displayCurrency, conversionRates) {
    override fun getPriceString(context: Context) = if (conversionRates.isNullOrEmpty()) {
        context.getString(
            R.string.price,
            domainAsset.getBasePrice(),
            domainAsset.baseCurrency.symbol
        )
    } else {
        context.getString(
            R.string.price,
            domainAsset.getBasePrice() * conversionRates.last().y,
            displayCurrency.symbol
        )
    }
}

data class UICurrency(
    override val domainAsset: Currency,
    override val displayCurrency: Currency,
    override val conversionRates: List<Entry>?
) : UIAsset(domainAsset, displayCurrency, conversionRates) {
    override fun getPriceString(context: Context) = if (conversionRates.isNullOrEmpty()) {
        context.getString(R.string.loading_conversion_rates)
    } else {
        context.getString(
            R.string.price,
            conversionRates.last().y,
            displayCurrency.symbol
        )
    }
}
