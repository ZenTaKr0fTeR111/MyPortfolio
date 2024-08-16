package com.example.myportfolio.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.myportfolio.data.db.AppDatabase
import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_portfolio"
        ).build()
    }

    @Provides
    fun provideConversionRateDao(database: AppDatabase): ConversionRateDao {
        return database.conversionRateDao()
    }
}
