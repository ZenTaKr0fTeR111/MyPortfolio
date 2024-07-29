package com.example.myportfolio.ui.models

import android.content.Context
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stock

sealed class UIAsset(
    open val domainAsset: Asset,
    open val displayCurrency: Currency
) {
    abstract fun getPriceString(context: Context): String
}

data class UIStock(
    override val domainAsset: Stock,
    override val displayCurrency: Currency
) : UIAsset(domainAsset, displayCurrency) {
    override fun getPriceString(context: Context) =
        context.getString(R.string.price, domainAsset.getBasePrice(), displayCurrency.symbol)
}

data class UIBond(
    override val domainAsset: Bond,
    override val displayCurrency: Currency
) : UIAsset(domainAsset, displayCurrency) {
    override fun getPriceString(context: Context) =
        context.getString(R.string.price, domainAsset.getBasePrice(), displayCurrency.symbol)
}

data class UICurrency(
    override val domainAsset: Currency,
    override val displayCurrency: Currency
) : UIAsset(domainAsset, displayCurrency) {
    override fun getPriceString(context: Context) =
        context.getString(R.string.price, 1.00, displayCurrency.symbol)
}
