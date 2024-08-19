package com.example.myportfolio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.data.db.conversion_rates.ConversionRateEntity
import com.example.myportfolio.data.db.conversion_rates.ConversionRateTypeConverters

@Database(entities = [ConversionRateEntity::class], version = 1)
@TypeConverters(ConversionRateTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionRateDao(): ConversionRateDao
}
