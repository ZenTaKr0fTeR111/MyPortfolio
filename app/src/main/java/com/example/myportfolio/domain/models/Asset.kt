package com.example.myportfolio.domain.models

sealed class Asset(
    open val id: Int,
    open val name: String
)

data class Currency(
    override val id: Int,
    override val name: String,
    val code: CurrencyCode,
    val symbol: String
) : Asset(id, name)

data class Stock(
    override val id: Int,
    override val name: String,
    val priceInSubunits: Int,
    val baseCurrency: Currency,
    val ticker: String
) : Asset(id, name) {
    fun getBasePrice() = priceInSubunits / 100.0
}

data class Bond(
    override val id: Int,
    override val name: String,
    val parInSubunits: Int,
    val baseCurrency: Currency,
    val code: String,
    val rate: Double
) : Asset(id, name) {
    fun getBasePrice() = parInSubunits / 100.0
}
