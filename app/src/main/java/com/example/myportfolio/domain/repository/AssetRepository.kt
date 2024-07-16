package com.example.myportfolio.domain.repository

import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Currency

interface AssetRepository {
    fun getAssets(): List<Asset>

    fun getAssetById(id: Int): Asset?

    fun getBaseCurrency(): Currency
}
