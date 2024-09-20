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
import java.util.Calendar

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Formatters {
    val BOND_DETAILS_FORMATTER by lazy { DateTimeFormatter.ofPattern("MMMM d, yyyy") }
    val API_ARGS_FORMATTER by lazy { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
}

fun LocalDate.safeFormat(formatter: DateTimeFormatter): String {
    return try {
        format(formatter)
    } catch (e: DateTimeException) {
        println(
            "Indicated problems with creating, querying or manipulating date-time object $this."
        )
        e.printStackTrace()
        return this.toString()
    }
}

fun getMillisTillMidnight(): Long {
    val midnight = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        add(Calendar.DAY_OF_MONTH, 1)
    }
    return midnight.timeInMillis - Calendar.getInstance().timeInMillis
}

fun String.parseApiDate(): LocalDate {
    return try {
        LocalDate.parse(this.substringBefore("T"))
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
    xAxis.textColor = context.getColor(R.color.chart)
    axisLeft.textColor = context.getColor(R.color.chart)
    axisRight.textColor = context.getColor(R.color.chart)
}

object ChartDateFormatter : ValueFormatter() {
    private const val CONVERSION_DATE_FORMAT = "d MMM"

    override fun getAxisLabel(floatTimeStamp: Float, axis: AxisBase?): String {
        return LocalDate.ofEpochDay(floatTimeStamp.toLong())
            .format(DateTimeFormatter.ofPattern(CONVERSION_DATE_FORMAT))
    }
}
