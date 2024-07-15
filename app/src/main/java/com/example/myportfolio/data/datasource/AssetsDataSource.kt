package com.example.myportfolio.data.datasource

import com.example.myportfolio.domain.models.Asset
import javax.inject.Inject

interface AssetsDataSource {
    fun getAssets(): List<Asset>
}

class AssetsSampleSource @Inject constructor() : AssetsDataSource {
    override fun getAssets() = assetsStorage
}
