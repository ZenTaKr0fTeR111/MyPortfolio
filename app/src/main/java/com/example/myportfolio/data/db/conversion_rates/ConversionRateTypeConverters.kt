package com.example.myportfolio.data.db.conversion_rates

import androidx.room.TypeConverter
import com.example.myportfolio.domain.models.CurrencyCode
import java.time.LocalDate

class ConversionRateTypeConverters {
    @TypeConverter
    fun localDateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun stringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun currencyCodeToString(code: CurrencyCode): String {
        return code.name
    }

    @TypeConverter
    fun stringToCurrencyCode(codeString: String): CurrencyCode {
        return CurrencyCode.valueOf(codeString)
    }
}
