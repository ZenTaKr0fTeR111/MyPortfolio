package com.example.myportfolio.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.myportfolio.CONVERSION_API_BASE_URL
import com.example.myportfolio.HTTP_CACHE_SIZE_KB
import com.example.myportfolio.TIMEOUT_SECONDS
import com.example.myportfolio.data.db.AppDatabase
import com.example.myportfolio.data.db.conversion_rates.ConversionRateDao
import com.example.myportfolio.data.remote.ConversionRateApiService
import com.example.myportfolio.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

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

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, HTTP_CACHE_SIZE_KB)
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .cache(cache)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CONVERSION_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun conversionApiService(retrofit: Retrofit): ConversionRateApiService {
        return retrofit.create(ConversionRateApiService::class.java)
    }
}
