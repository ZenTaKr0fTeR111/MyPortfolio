package com.example.myportfolio.domain.models

enum class CurrencyCode(val id: Long) {
    USD(431),
    EUR(451),
    GBP(429),
    CHF(426),
    BYN(0);

    companion object {
        private val codeMap = entries.associateBy(CurrencyCode::id)

        fun getStringValues() = entries.map { it.name }

        /**
         *  @throws IllegalArgumentException if currency with id [id] is not defined
         */
        fun getById(id: Long) = codeMap[id]
            ?: throw IllegalArgumentException("Currency with id $id is not found.")
    }
}

val currencies = mutableListOf(
    Currency(431, "United States Dollar", CurrencyCode.USD, "$", 100),
    Currency(451, "Euro", CurrencyCode.EUR, "€", 100),
    Currency(429, "British Pound Sterling", CurrencyCode.GBP, "£", 100),
    Currency(426, "Swiss Franc", CurrencyCode.CHF, "₣", 100),
    Currency(101, "Belarusian Ruble", CurrencyCode.BYN, "Br", 100)
).associateBy { it.code }
