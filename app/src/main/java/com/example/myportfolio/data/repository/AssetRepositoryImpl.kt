package com.example.myportfolio.data.repository

import com.example.myportfolio.data.datasource.AssetsDataSource
import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.repository.AssetRepository
import javax.inject.Inject

class AssetRepositoryImpl @Inject constructor(
    private val source: AssetsDataSource
) : AssetRepository {
    override fun getAssets(): List<Asset> {
        return source.getAssets()
    }

    override fun getAssetById(id: Int): Asset? {
        return source.getAssets().find { it.id == id }
    }
}
