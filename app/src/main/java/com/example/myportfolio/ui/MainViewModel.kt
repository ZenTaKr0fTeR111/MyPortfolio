package com.example.myportfolio.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.myportfolio.data.DAILY_RATES_WORK_NAME
import com.example.myportfolio.domain.interactors.ConversionInteractor
import com.example.myportfolio.domain.interactors.SettingsInteractor
import com.example.myportfolio.domain.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val conversionInteractor: ConversionInteractor,
    private val workManager: WorkManager
) : ViewModel() {
    private val _defaultCurrency = MutableLiveData<Currency>()
    val defaultCurrency: LiveData<Currency>
        get() = _defaultCurrency

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean>
        get() = _isDarkMode

    init {
        fetchDefaultCurrency()
        fetchDarkMode()
        scheduleDailyRatesRetrieval()
    }

    fun saveDefaultCurrency(currency: Currency) {
        viewModelScope.launch {
            settingsInteractor.invokeSetDefaultCurrency(currency)
        }
    }

    fun toggleDarkMode(toggled: Boolean) {
        viewModelScope.launch {
            settingsInteractor.invokeToggleNightMode(toggled)
        }
    }

    private fun scheduleDailyRatesRetrieval() {
        viewModelScope.launch {
            workManager.getWorkInfosByTagFlow(DAILY_RATES_WORK_NAME).collect { workInfos ->
                if (workInfos.isNullOrEmpty()) {
                    conversionInteractor.invokeScheduleDailyRatesFetching()
                }
            }
        }
    }

    private fun fetchDefaultCurrency() {
        viewModelScope.launch {
            settingsInteractor.invokeFetchDefaultCurrency().collect { newDefaultCurrency ->
                _defaultCurrency.value = newDefaultCurrency
            }
        }
    }

    private fun fetchDarkMode() {
        viewModelScope.launch {
            settingsInteractor.invokeFetchNightMode().collect { newIsDarkMode ->
                _isDarkMode.value = newIsDarkMode
            }
        }
    }
}
