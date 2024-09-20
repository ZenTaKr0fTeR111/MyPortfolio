package com.example.myportfolio.ui.assets_screen.detailed_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.domain.interactors.AssetInteractor
import com.example.myportfolio.domain.interactors.ConversionInteractor
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.Stock
import com.example.myportfolio.domain.models.TimePeriod
import com.example.myportfolio.domain.models.currencies
import com.example.myportfolio.mapConversionRatesToEntries
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock
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
    private val _conversionRates = MutableLiveData<List<Entry>>()
    private var _convertToCurrency = MutableLiveData<Currency>()
    private var _period = TimePeriod.WEEK
    val period: TimePeriod
        get() = _period

    val asset = MediatorLiveData<UIAsset>()

    fun fetchAsset(assetID: Int, appCurrency: LiveData<Currency>) {
        viewModelScope.launch {
            if (_asset.value == null) {
                _asset.value = interactor.invokeFetchAssetById(assetID)
            }
            if (_convertToCurrency.value == null) {
                _convertToCurrency.value = appCurrency.value
                fetchConversionRates()
                initAsset(_convertToCurrency)
            }
        }
    }

    private fun initAsset(appCurrency: LiveData<Currency>) {
        if (asset.value != null) return
        asset.apply {
            addSource(_asset) { newAsset ->
                value = mapAsset(newAsset, appCurrency.value, _conversionRates.value)
            }
            addSource(appCurrency) { newDefaultCurrency ->
                value = mapAsset(_asset.value, newDefaultCurrency, _conversionRates.value)
            }
            addSource(_conversionRates) { newRates ->
                value = mapAsset(_asset.value, appCurrency.value, newRates)
            }
        }
    }

    private fun mapAsset(
        asset: Asset?,
        appCurrency: Currency?,
        conversionRateList: List<Entry>?
    ): UIAsset? {
        return if (asset == null || appCurrency == null) null
        else when (asset) {
            is Stock -> UIStock(asset, appCurrency, conversionRateList)
            is Bond -> UIBond(asset, appCurrency, conversionRateList)
            is Currency -> UICurrency(asset, appCurrency, conversionRateList)
        }
    }

    fun changePeriod(newPeriod: TimePeriod) {
        _period = newPeriod
        fetchConversionRates()
    }

    fun changeConvertToCurrency(newCurrencyCode: CurrencyCode) {
        _convertToCurrency.value = currencies[newCurrencyCode]
        fetchConversionRates()
    }

    private fun fetchConversionRates() {
        viewModelScope.launch {
            val sourceCurrency = when (val asset = _asset.value!!) {
                is Currency -> asset.code
                is Bond -> asset.baseCurrency.code
                is Stock -> asset.baseCurrency.code
            }
            val mappedRates = withContext(Dispatchers.Default) {
                conversionInteractor.invokeFetchConversionRates(
                    sourceCurrency,
                    _convertToCurrency.value?.code ?: CurrencyCode.USD,
                    period
                ).mapConversionRatesToEntries()
            }
            _conversionRates.value = mappedRates
        }
    }
}
