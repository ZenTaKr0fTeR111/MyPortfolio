package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.currencies
import com.example.myportfolio.domain.repository.SettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsInteractor @Inject constructor(
    private val settingsStore: SettingsRepository
) {
    suspend fun invokeFetchDefaultCurrency(): Flow<Currency> {
        return settingsStore.getDefaultCurrency().map { currencyName ->
            currencies.getValue(CurrencyCode.valueOf(currencyName))
        }
    }

    suspend fun invokeSetDefaultCurrency(currency: Currency) {
        settingsStore.setDefaultCurrency(currency.code.name)
    }

    suspend fun invokeFetchNightMode(): Flow<Boolean> {
        return settingsStore.getDarkMode()
    }

    suspend fun invokeToggleNightMode(isDarkMode: Boolean) {
        settingsStore.setDarkMode(isDarkMode)
    }
}
