package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.repository.AssetRepository
import javax.inject.Inject

class AssetInteractor @Inject constructor(
    private val repository: AssetRepository
) {
    fun invokeFetchingAssets() = repository.getAssets()

    fun invokeFetchingBaseCurrency() = repository.getBaseCurrency()
}
