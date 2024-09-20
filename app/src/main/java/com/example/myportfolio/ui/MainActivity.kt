package com.example.myportfolio.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.currencies
import com.example.myportfolio.ui.assets_screen.AssetListScreen
import com.example.myportfolio.ui.assets_screen.detailed_screen.DetailedBondScreen
import com.example.myportfolio.ui.assets_screen.detailed_screen.DetailedCurrencyScreen
import com.example.myportfolio.ui.assets_screen.detailed_screen.DetailedStockScreen
import com.example.myportfolio.ui.compose.ActionBar
import com.example.myportfolio.ui.compose.AppTheme
import com.example.myportfolio.ui.compose.BottomNavigationBar
import com.example.myportfolio.ui.compose.DetailedBondScreenRoute
import com.example.myportfolio.ui.compose.DetailedCurrencyScreenRoute
import com.example.myportfolio.ui.compose.DetailedStockScreenRoute
import com.example.myportfolio.ui.compose.MainAssetListScreenMainRoute
import com.example.myportfolio.ui.compose.MainHomeScreenMainRoute
import com.example.myportfolio.ui.compose.MainPortfolioListScreenMainRoute
import com.example.myportfolio.ui.compose.SettingsScreenMainRoute
import com.example.myportfolio.ui.home_screen.HomeScreen
import com.example.myportfolio.ui.portfolio_screen.PortfolioScreen
import com.example.myportfolio.ui.settings_screen.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val darkMode by viewModel.isDarkMode.observeAsState()

            AppTheme(
                darkTheme = darkMode ?: isSystemInDarkTheme()
            ) {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        ActionBar(
                            title = stringResource(R.string.app_name),
                            navController = navController
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    MainNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun MainNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = MainHomeScreenMainRoute,
            modifier = modifier
        ) {
            composable<MainHomeScreenMainRoute> {
                HomeScreen()
            }
            composable<MainAssetListScreenMainRoute> {
                AssetListScreen(
                    navController = navController,
                    defaultCurrency = viewModel.defaultCurrency
                )
            }
            composable<MainPortfolioListScreenMainRoute> {
                PortfolioScreen()
            }
            composable<SettingsScreenMainRoute> {
                SettingsScreen(
                    viewModel = viewModel,
                    onPickCurrency = { setupCurrencyChoiceDialog() }
                )
            }

            composable<DetailedCurrencyScreenRoute> { entry ->
                val args = entry.toRoute<DetailedCurrencyScreenRoute>()
                DetailedCurrencyScreen(
                    assetId = args.assetId,
                    mainViewModel = viewModel
                )
            }
            composable<DetailedStockScreenRoute> { entry ->
                val args = entry.toRoute<DetailedStockScreenRoute>()
                DetailedStockScreen(
                    assetId = args.assetId,
                    mainViewModel = viewModel
                )
            }
            composable<DetailedBondScreenRoute> { entry ->
                val args = entry.toRoute<DetailedBondScreenRoute>()
                DetailedBondScreen(
                    assetId = args.assetId,
                    mainViewModel = viewModel
                )
            }
        }
    }

    private fun setupCurrencyChoiceDialog() {
        val options = CurrencyCode.getStringValues().toTypedArray()
        val selectedOption = viewModel.defaultCurrency.value?.code?.ordinal ?: 0
        AlertDialog.Builder(this)
            .setTitle(R.string.currency_picker_title)
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setSingleChoiceItems(options, selectedOption) { dialog, selectedIndex ->
                val currency = currencies[CurrencyCode.entries[selectedIndex]]
                currency?.let {
                    viewModel.saveDefaultCurrency(currency)
                }
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
