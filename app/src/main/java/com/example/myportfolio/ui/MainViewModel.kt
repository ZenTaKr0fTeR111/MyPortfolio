package com.example.myportfolio.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.domain.interactors.SettingsInteractor
import com.example.myportfolio.domain.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
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
    }

    fun saveDefaultCurrency(currency: Currency) {
        viewModelScope.launch {
            settingsInteractor.invokeSetDefaultCurrency(currency)
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            settingsInteractor.invokeToggleNightMode(!isDarkMode.value!!)
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
