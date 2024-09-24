package com.example.myportfolio.domain.models

enum class TimePeriod(val days: Int) {
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365);

    companion object {
        fun getPeriodsForDetailedCurrencyScreen(): List<TimePeriod> {
            return listOf(WEEK, MONTH, YEAR)
        }
    }
}
