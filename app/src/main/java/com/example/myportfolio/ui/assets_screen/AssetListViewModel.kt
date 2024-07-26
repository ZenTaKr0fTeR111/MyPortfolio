package com.example.myportfolio.ui.assets_screen

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myportfolio.domain.interactors.AssetInteractor
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Bond
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stock
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val interactor: AssetInteractor
) : ViewModel() {
    private val _defaultCurrency = MutableLiveData<Currency>()

    private val _assets = MutableLiveData<List<Asset>>()
    val assets = MediatorLiveData<List<UIAsset>>().apply {
        addSource(_assets) { newAssets ->
            value = mapAssets(newAssets, _defaultCurrency.value)
        }
        addSource(_defaultCurrency) { newDefaultCurrency ->
            value = mapAssets(_assets.value, newDefaultCurrency)
        }
    }

    init {
        fetchAssets()
        fetchBaseCurrency()
    }

    private fun fetchAssets() {
        _assets.value = interactor.invokeFetchingAssets()
    }

    private fun fetchBaseCurrency() {
        _defaultCurrency.value = interactor.invokeFetchingBaseCurrency()
    }

    private fun mapAssets(assets: List<Asset>?, currency: Currency?): List<UIAsset>? {
        return if (assets == null || currency == null) null
        else assets.map { asset ->
            when (asset) {
                is Stock -> UIStock(asset, currency)
                is Bond -> UIBond(asset, currency)
                is Currency -> UICurrency(asset, currency)
            }
        }
    }
}
