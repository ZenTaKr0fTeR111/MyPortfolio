package com.example.myportfolio.ui.assets_screen.detailed_screen

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter : ValueFormatter() {
    override fun getAxisLabel(floatTimeStamp: Float, axis: AxisBase?): String {
        return LocalDate.ofEpochDay(floatTimeStamp.toLong())
            .format(DateTimeFormatter.ofPattern("dd MMM"))
    }
}
