package com.example.myportfolio.ui.compose

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute

// Main Screens
@Serializable
data object MainHomeScreenMainRoute : AppRoute()
@Serializable
data object MainAssetListScreenMainRoute : AppRoute()
@Serializable
data object MainPortfolioListScreenMainRoute : AppRoute()
@Serializable
data object SettingsScreenMainRoute : AppRoute()

// Detailed Screens
@Serializable
data class DetailedCurrencyScreenRoute(val assetId: Int) : AppRoute()
@Serializable
data class DetailedStockScreenRoute(val assetId: Int) : AppRoute()
@Serializable
data class DetailedBondScreenRoute(val assetId: Int) : AppRoute()
