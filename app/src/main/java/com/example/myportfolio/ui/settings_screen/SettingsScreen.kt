package com.example.myportfolio.ui.settings_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.compose.previewCurrency

@Composable
fun SettingsScreen(
    onPickCurrency: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val defaultCurrency by viewModel.defaultCurrency.observeAsState()
    val darkTheme by viewModel.isDarkMode.observeAsState()

    SettingsScreenContent(
        onPickCurrency = onPickCurrency,
        onCheckedChanged = { checked -> viewModel.toggleDarkMode(checked) },
        defaultCurrency = defaultCurrency,
        darkTheme = darkTheme,
        modifier = modifier
    )
}

@Composable
fun SettingsScreenContent(
    onPickCurrency: () -> Unit,
    onCheckedChanged: (Boolean) -> Unit,
    defaultCurrency: Currency?,
    darkTheme: Boolean?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        val rowHeight = 55.dp
        HorizontalDivider(thickness = 1.dp)
        CurrencySetting(
            currencyCode = defaultCurrency?.code.toString(),
            onClick = onPickCurrency,
            modifier = Modifier.height(rowHeight)
        )
        HorizontalDivider(thickness = 1.dp)
        ThemeSetting(
            isNightMode = darkTheme ?: false,
            onCheckedChanged = onCheckedChanged,
            modifier = Modifier.height(rowHeight)
        )
        HorizontalDivider(thickness = 1.dp)
    }
}

@Composable
fun CurrencySetting(
    currencyCode: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingRow(
        settingLabel = stringResource(R.string.setting_currency_label),
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Text(
            text = currencyCode,
            fontSize = dimensionResource(R.dimen.settings_text_size).value.sp
        )
    }
}

@Composable
fun ThemeSetting(
    isNightMode: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SettingRow(
        settingLabel = stringResource(R.string.setting_nightmode_label),
        modifier = modifier
    ) {
        Switch(
            checked = isNightMode,
            onCheckedChange = onCheckedChanged
        )
    }
}

@Preview
@Composable
fun CurrencySettingPreview() {
    Surface {
        CurrencySetting(currencyCode = "USD", onClick = {})
    }
}

@Preview
@Composable
fun ThemeSettingPreview() {
    Surface {
        ThemeSetting(isNightMode = true, onCheckedChanged = {})
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Surface {
        SettingsScreenContent(
            onPickCurrency = {},
            onCheckedChanged = {},
            defaultCurrency = previewCurrency.domainAsset,
            darkTheme = false
        )
    }
}
