package com.example.myportfolio.ui.assets_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.ui.assets_screen.list_items.BondListItem
import com.example.myportfolio.ui.assets_screen.list_items.CurrencyListItem
import com.example.myportfolio.ui.assets_screen.list_items.StockListItem
import com.example.myportfolio.ui.compose.DetailedBondScreenRoute
import com.example.myportfolio.ui.compose.DetailedCurrencyScreenRoute
import com.example.myportfolio.ui.compose.DetailedStockScreenRoute
import com.example.myportfolio.ui.models.UIAsset
import com.example.myportfolio.ui.models.UIBond
import com.example.myportfolio.ui.models.UICurrency
import com.example.myportfolio.ui.models.UIStock

@Composable
fun AssetListScreen(
    navController: NavHostController,
    defaultCurrency: LiveData<Currency>,
    modifier: Modifier = Modifier,
    viewModel: AssetListViewModel = hiltViewModel()
) {
    viewModel.initAssets(defaultCurrency)
    val listOfAssets by viewModel.assets.observeAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (listOfAssets.isNullOrEmpty()) {
            CircularProgressIndicator()
        } else {
            AssetList(
                assetList = listOfAssets!!,
                navController = navController
            )
        }
    }
}

@Composable
fun AssetList(
    assetList: List<UIAsset>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(items = assetList) { index, asset ->
            when (asset) {
                is UIStock -> {
                    StockListItem(
                        stock = asset,
                        onClick = {
                            navController.navigate(
                                DetailedStockScreenRoute(
                                    assetId = asset.domainAsset.id
                                )
                            )
                        }
                    )
                }
                is UIBond -> {
                    BondListItem(
                        bond = asset,
                        onClick = {
                            navController.navigate(
                                DetailedBondScreenRoute(
                                    assetId = asset.domainAsset.id
                                )
                            )
                        }
                    )
                }
                is UICurrency -> {
                    CurrencyListItem(
                        currency = asset,
                        onClick = {
                            navController.navigate(
                                DetailedCurrencyScreenRoute(
                                    assetId = asset.domainAsset.id
                                )
                            )
                        }
                    )
                }
            }
            if (index < assetList.size - 1) {
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }
}
