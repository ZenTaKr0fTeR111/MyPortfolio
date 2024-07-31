package com.example.myportfolio.domain.models

enum class CurrencyCode {
    USD,
    EUR,
    GBP,
    CHF,
    BYN;

    companion object {
        fun getStringValues() = entries.map { it.name }
    }
}

val currencies = mutableListOf(
    Currency(100, "United States Dollar", CurrencyCode.USD, "$"),
    Currency(101, "Euro", CurrencyCode.EUR, "€"),
    Currency(102, "British Pound Sterling", CurrencyCode.GBP, "£"),
    Currency(103, "Swiss Franc", CurrencyCode.CHF, "₣"),
    Currency(104, "Belarusian Ruble", CurrencyCode.BYN, "Br")
).associateBy { it.code }
