package com.example.myportfolio.domain.interactors

import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.repository.AssetRepository
import javax.inject.Inject

class AssetInteractor @Inject constructor(
    private val repository: AssetRepository
) {
    suspend fun invokeFetchAssets() = repository.getAssets()

    suspend fun invokeFetchAssetById(assetID: Int): Asset {
        return repository.getAssetById(assetID)
            ?: throw IllegalArgumentException("No asset with specified id $assetID found")
    }
}
