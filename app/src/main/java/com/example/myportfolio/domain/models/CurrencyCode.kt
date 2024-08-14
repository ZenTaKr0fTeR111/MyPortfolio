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
    Currency(431, "United States Dollar", CurrencyCode.USD, "$", 100),
    Currency(451, "Euro", CurrencyCode.EUR, "€", 100),
    Currency(429, "British Pound Sterling", CurrencyCode.GBP, "£", 100),
    Currency(426, "Swiss Franc", CurrencyCode.CHF, "₣", 100),
    Currency(101, "Belarusian Ruble", CurrencyCode.BYN, "Br", 100)
).associateBy { it.code }
