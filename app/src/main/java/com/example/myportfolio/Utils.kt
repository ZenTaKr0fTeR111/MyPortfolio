package com.example.myportfolio

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object DateTimeUtils : ValueFormatter() {
    private const val BOND_DETAILS_FORMAT = "MMMM d, yyyy"
    private const val CONVERSION_DATE_FORMAT = "dd MMM"

    fun formatBondDetails(date: LocalDate): String {
        return try {
            date.format(DateTimeFormatter.ofPattern(BOND_DETAILS_FORMAT))
        } catch (e: DateTimeException) {
            println(
                "Indicated problems with creating, querying or manipulating date-time object $date"
            )
            e.printStackTrace()
            return date.toString()
        }
    }

    override fun getAxisLabel(floatTimeStamp: Float, axis: AxisBase?): String {
        return LocalDate.ofEpochDay(floatTimeStamp.toLong())
            .format(DateTimeFormatter.ofPattern(CONVERSION_DATE_FORMAT))
    }
}
