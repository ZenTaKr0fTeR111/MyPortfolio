package com.example.myportfolio

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myportfolio.domain.models.ConversionRate
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

const val CONVERSION_API_BASE_URL = "https://api.nbrb.by/"
const val TIMEOUT_SECONDS: Long = 15
const val HTTP_CACHE_SIZE_KB: Long = 7 * 1024

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class FormatSubject {
    BOND_DETAILS,
    API_ARGS
}

fun LocalDate.formatAppDetails(formatSubject: FormatSubject): String {
    return try {
        when (formatSubject) {
            FormatSubject.BOND_DETAILS -> format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            FormatSubject.API_ARGS -> format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
    } catch (e: DateTimeException) {
        println(
            "Indicated problems with creating, querying or manipulating date-time object $this."
        )
        e.printStackTrace()
        return this.toString()
    }
}

fun String.parseApiDate(): LocalDate {
    return try {
        LocalDate.parse(this.split("T")[0])
    } catch (e: DateTimeParseException) {
        println("Error parsing string $this into local data instance.")
        e.printStackTrace()
        LocalDate.now()
    }
}

fun List<ConversionRate>.mapConversionRatesToEntries(): List<Entry> {
    return map { conversionRate ->
        Entry(conversionRate.date.toEpochDay().toFloat(), conversionRate.rate.toFloat())
    }.sortedBy { it.x }
}

fun LineChart.configureChart(context: Context) {
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    legend.isEnabled = false
    description = null
    xAxis.textColor = context.getColor(R.color.app_text)
    axisLeft.textColor = context.getColor(R.color.app_text)
    axisRight.textColor = context.getColor(R.color.app_text)
}

object ChartDateFormatter : ValueFormatter() {
    private const val CONVERSION_DATE_FORMAT = "d MMM"

    override fun getAxisLabel(floatTimeStamp: Float, axis: AxisBase?): String {
        return LocalDate.ofEpochDay(floatTimeStamp.toLong())
            .format(DateTimeFormatter.ofPattern(CONVERSION_DATE_FORMAT))
    }
}
