package com.example.myportfolio.domain.models

abstract class Asset(
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
    val price: Int,
    val baseCurrency: Currency,
    val ticker: String
) : Asset(id, name) {
    fun getPrice() = (price / 100.toDouble()).toString() + baseCurrency.symbol
}

data class Bond(
    override val id: Int,
    override val name: String,
    val par: Int,
    val baseCurrency: Currency,
    val code: String,
    val rate: Double
) : Asset(id, name) {
    fun getPrice() = (par / 100.toDouble()).toString() + baseCurrency.symbol
}
