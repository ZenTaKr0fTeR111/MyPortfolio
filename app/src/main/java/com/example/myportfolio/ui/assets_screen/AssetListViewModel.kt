package com.example.myportfolio.ui.assets_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val interactor: AssetInteractor
) : ViewModel() {
    private val _assets = MutableLiveData<List<Asset>>()
    val assets = MediatorLiveData<List<UIAsset>>()

    init {
        fetchAssets()
    }

    fun initAssets(currency: LiveData<Currency>) {
        if (assets.value != null) return
        assets.apply {
            addSource(_assets) { newAssets ->
                value = mapAssets(newAssets, currency.value)
            }
            addSource(currency) { newDefaultCurrency ->
                value = mapAssets(_assets.value, newDefaultCurrency)
            }
        }
    }

    private fun fetchAssets() {
        viewModelScope.launch {
            _assets.value = interactor.invokeFetchAssets()
        }
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
