package com.example.myportfolio.domain.repository

import com.example.myportfolio.domain.models.Asset

interface AssetRepository {
    fun getAssets(): List<Asset>

    fun getAssetById(id: Int): Asset?
}
