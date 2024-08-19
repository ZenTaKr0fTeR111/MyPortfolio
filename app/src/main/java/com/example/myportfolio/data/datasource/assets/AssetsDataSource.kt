package com.example.myportfolio.data.datasource.assets

import com.example.myportfolio.domain.models.Asset
import com.example.myportfolio.domain.models.Currency

interface AssetsDataSource {
    fun getAssets(): List<Asset>

    fun getAssetById(id: Int): Asset?

    fun getBaseCurrency(): Currency
}
