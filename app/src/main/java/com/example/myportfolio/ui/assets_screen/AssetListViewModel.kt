package com.example.myportfolio.ui.assets_screen

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
import com.example.myportfolio.domain.models.Stock
import com.example.myportfolio.domain.models.TimePeriod
import com.example.myportfolio.mapConversionRatesToEntries
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val assetInteractor: AssetInteractor,
    private val conversionInteractor: ConversionInteractor
) : ViewModel() {
    private val _assets = MutableLiveData<List<Asset>>()
    val assets = MediatorLiveData<List<UIAsset>>()

    init {
        fetchAssets()
    }

    fun initAssets(appCurrency: LiveData<Currency>) {
        if (assets.value != null) return
        assets.apply {
            addSource(_assets) { newAssets ->
                viewModelScope.launch {
                    value = mapAssets(newAssets, appCurrency.value)
                }
            }
            addSource(appCurrency) { newDefaultCurrency ->
                viewModelScope.launch {
                    value = mapAssets(_assets.value, newDefaultCurrency)
                }
            }
        }
    }

    private fun fetchAssets() {
        viewModelScope.launch {
            _assets.value = assetInteractor.invokeFetchAssets()
        }
    }

    private suspend fun mapAssets(
        assets: List<Asset>?,
        appCurrency: Currency?
    ): List<UIAsset>? {
        return if (assets == null || appCurrency == null) null
        else {
            assets.map { asset ->
                when (asset) {
                    is Stock -> {
                        val conversionRate = conversionInteractor.invokeFetchConversionRates(
                            asset.baseCurrency.code,
                            appCurrency.code,
                            TimePeriod.DAY
                        )
                        UIStock(asset, appCurrency, conversionRate.mapConversionRatesToEntries())
                    }
                    is Bond -> {
                        val conversionRate = conversionInteractor.invokeFetchConversionRates(
                            asset.baseCurrency.code,
                            appCurrency.code,
                            TimePeriod.DAY
                        )
                        UIBond(asset, appCurrency, conversionRate.mapConversionRatesToEntries())
                    }
                    is Currency -> {
                        val conversionRate = conversionInteractor.invokeFetchConversionRates(
                            asset.code,
                            appCurrency.code,
                            TimePeriod.DAY
                        )
                        UICurrency(asset, appCurrency, conversionRate.mapConversionRatesToEntries())
                    }
                }
            }
        }
    }
}
