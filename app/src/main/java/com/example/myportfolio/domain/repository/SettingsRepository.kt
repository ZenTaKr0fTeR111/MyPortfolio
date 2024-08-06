package com.example.myportfolio.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getDefaultCurrency(): Flow<String>

    suspend fun setDefaultCurrency(currencyCodeName: String)

    suspend fun getDarkMode(): Flow<Boolean>

    suspend fun setDarkMode(isNightMode: Boolean)
}
