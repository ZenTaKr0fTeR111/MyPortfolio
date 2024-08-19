package com.example.myportfolio.domain.models

enum class CurrencyCode(val id: Long) {
    USD(431),
    EUR(451),
    GBP(429),
    CHF(426),
    BYN(0);

    companion object {
        fun getStringValues() = entries.map { it.name }

        fun getById(id: Long): CurrencyCode {
            return entries.find { it.id == id }
                ?: throw IllegalArgumentException("Currency with id $id is not found.")
        }
    }
}

val currencies = mutableListOf(
    Currency(431, "United States Dollar", CurrencyCode.USD, "$", 100),
    Currency(451, "Euro", CurrencyCode.EUR, "€", 100),
    Currency(429, "British Pound Sterling", CurrencyCode.GBP, "£", 100),
    Currency(426, "Swiss Franc", CurrencyCode.CHF, "₣", 100),
    Currency(101, "Belarusian Ruble", CurrencyCode.BYN, "Br", 100)
).associateBy { it.code }
