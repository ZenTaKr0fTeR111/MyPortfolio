package com.example.myportfolio.ui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myportfolio.R

data class NavigationBarItem(
    @DrawableRes val iconRes: Int,
    @StringRes val descriptionRes: Int,
    val route: AppRoute
)

val defaultMenuItems = listOf(
    NavigationBarItem(
        iconRes = R.drawable.home_icon,
        descriptionRes = R.string.text_home,
        route = MainHomeScreenMainRoute
    ),
    NavigationBarItem(
        iconRes = R.drawable.asset_list_icon,
        descriptionRes = R.string.text_asset_list,
        route = MainAssetListScreenMainRoute
    ),
    NavigationBarItem(
        iconRes = R.drawable.portfolio_icon,
        descriptionRes = R.string.text_portfolio_list,
        route = MainPortfolioListScreenMainRoute
    ),
    NavigationBarItem(
        iconRes = R.drawable.settings_icon,
        descriptionRes = R.string.text_settings,
        route = SettingsScreenMainRoute
    )
)
