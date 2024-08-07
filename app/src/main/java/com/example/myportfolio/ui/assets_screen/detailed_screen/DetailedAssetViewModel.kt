package com.example.myportfolio.ui.assets_screen.detailed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.domain.interactors.AssetInteractor
import com.example.myportfolio.domain.interactors.ConversionInteractor
import com.example.myportfolio.domain.interactors.ConversionInteractor.Period
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.ConversionRate
import com.example.myportfolio.domain.models.CurrencyCode
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DetailedAssetViewModel @Inject constructor(
    private val interactor: AssetInteractor,
    private val conversionInteractor: ConversionInteractor
) : ViewModel() {
    private val _asset = MutableLiveData<Asset>()
    val asset: LiveData<Asset>
        get() = _asset

    private val _conversionRates = MutableLiveData<List<Entry>>()
    val conversionRates: LiveData<List<Entry>>
        get() = _conversionRates

    fun fetchAsset(assetID: Int) {
        viewModelScope.launch {
            _asset.value = interactor.invokeFetchAssetById(assetID)
        }
    }

    fun fetchConversionRates(sourceCurrency: CurrencyCode, targetCurrency: CurrencyCode) {
        viewModelScope.launch {
            _conversionRates.value = ConversionRateMapper().mapConversionRatesToEntries(
                conversionInteractor.invokeFetchConversionRates(
                    sourceCurrency,
                    targetCurrency,
                    Period.WEEK
                )
            )
        }
    }

    class ConversionRateMapper {
        suspend fun mapConversionRatesToEntries(rates: List<ConversionRate>): List<Entry> {
            return withContext(Dispatchers.Default) {
                rates.map { conversionRate ->
                    Entry(conversionRate.date.toEpochDay().toFloat(), conversionRate.rate.toFloat())
                }.sortedBy { it.x }
            }
        }
    }
}
