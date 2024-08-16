package com.example.myportfolio.data.db.conversion_rates

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myportfolio.domain.models.CurrencyCode
import java.time.LocalDate

@Entity(tableName = "conversion_rates")
data class ConversionRateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "currency") val sourceCurrency: CurrencyCode,
    @ColumnInfo(name = "rate") val rate: Double,
    @ColumnInfo(name = "date") val date: LocalDate
)
