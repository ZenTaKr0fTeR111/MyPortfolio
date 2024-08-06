package com.example.myportfolio.domain.models

import java.time.LocalDate

sealed class Asset(
    open val id: Int,
    open val name: String
)

data class Currency(
    override val id: Int,
    override val name: String,
    val code: CurrencyCode,
    val symbol: String,
    val subunitShare: Int
) : Asset(id, name)

data class Stock(
    override val id: Int,
    override val name: String,
    val priceInSubunits: Int,
    val baseCurrency: Currency,
    val ticker: String
) : Asset(id, name) {
    fun getBasePrice() = priceInSubunits / baseCurrency.subunitShare.toDouble()
}

data class Bond(
    override val id: Int,
    override val name: String,
    val parInSubunits: Int,
    val baseCurrency: Currency,
    val code: String,
    val rate: Double,
    val dateOfIssuance: LocalDate,
    val yearsTillMaturity: Long
) : Asset(id, name) {
    fun getBasePrice() = parInSubunits / baseCurrency.subunitShare.toDouble()
}
