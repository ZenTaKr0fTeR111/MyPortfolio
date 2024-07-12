package com.example.myportfolio.ui.assets_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myportfolio.domain.interactors.AssetInteractor
import com.example.myportfolio.domain.models.Asset
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AssetListViewModel @Inject constructor(
    private val interactor: AssetInteractor
) : ViewModel() {
    private val _assets = MutableLiveData<List<Asset>>()
    val assets: LiveData<List<Asset>>
        get() = _assets

    init {
        _assets.value = getAssets()
    }

    private fun getAssets() = interactor.getAssets()
}
