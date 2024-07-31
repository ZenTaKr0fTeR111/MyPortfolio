package com.example.myportfolio.domain.repository

import com.example.myportfolio.domain.models.Asset

interface AssetRepository {
    suspend fun getAssets(): List<Asset>

    suspend fun getAssetById(id: Int): Asset?
}
