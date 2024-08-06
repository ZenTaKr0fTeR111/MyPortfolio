package com.example.myportfolio.ui.assets_screen.detailed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.domain.interactors.AssetInteractor
import com.example.myportfolio.domain.interactors.ConversionInteractor
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.CurrencyCode
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailedAssetViewModel @Inject constructor(
    private val interactor: AssetInteractor,
    private val conversionInteractor: ConversionInteractor
) : ViewModel() {
    private var _asset = MutableLiveData<Asset>()
    val asset: LiveData<Asset>
        get() = _asset

    private var _conversionRates = MutableLiveData<List<Entry>>()
    val conversionRates: LiveData<List<Entry>>
        get() = _conversionRates

    fun fetchAsset(assetID: Int) {
        viewModelScope.launch {
            _asset.value = interactor.invokeFetchAssetById(assetID)
        }
    }

    fun fetchConversionRates(sourceCurrency: CurrencyCode, targetCurrency: CurrencyCode) {
        viewModelScope.launch {
            val rates = arrayListOf<Entry>()
            for (
                cRate in conversionInteractor.invokeFetchWeeklyRate(sourceCurrency, targetCurrency)
            ) {
                rates.add(Entry(cRate.date.toEpochDay().toFloat(), cRate.rate.toFloat()))
            }
            _conversionRates.value = rates.sortedBy { it.x }
        }
    }
}
