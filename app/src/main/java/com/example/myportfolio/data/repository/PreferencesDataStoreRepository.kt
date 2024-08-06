package com.example.myportfolio.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.repository.SettingsRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PreferencesDataStoreRepository @Inject constructor(
    private val settingsStore: DataStore<Preferences>
) : SettingsRepository {
    companion object {
        val SETTINGS_CURRENCY_KEY = stringPreferencesKey("default_currency")
        val SETTINGS_THEME_KEY = booleanPreferencesKey("night_mode")
    }

    override suspend fun getDefaultCurrency(): Flow<String> {
        return settingsStore.data
            .catch { e ->
                if (e !is IOException) throw e

                e.printStackTrace()
                emit(emptyPreferences())
            }
            .map { prefs ->
                prefs[SETTINGS_CURRENCY_KEY] ?: CurrencyCode.USD.name
            }
    }

    override suspend fun setDefaultCurrency(currencyCodeName: String) {
        settingsStore.edit { prefs ->
            prefs[SETTINGS_CURRENCY_KEY] = currencyCodeName
        }
    }

    override suspend fun getDarkMode(): Flow<Boolean> {
        return settingsStore.data
            .catch { e ->
                if (e !is IOException) throw e

                e.printStackTrace()
                emit(emptyPreferences())
            }
            .map { prefs ->
                prefs[SETTINGS_THEME_KEY] ?: false
            }
    }

    override suspend fun setDarkMode(isNightMode: Boolean) {
        settingsStore.edit { prefs ->
            prefs[SETTINGS_THEME_KEY] = isNightMode
        }
    }
}
