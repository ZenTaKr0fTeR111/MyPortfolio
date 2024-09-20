package com.example.myportfolio.ui.assets_screen.detailed_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myportfolio.ChartDateFormatter
import com.example.myportfolio.R
import com.example.myportfolio.configureChart
import com.example.myportfolio.domain.models.CurrencyCode
import com.example.myportfolio.domain.models.TimePeriod
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.assets_screen.detailed_screen.composables.MoneyPickerRow
import com.example.myportfolio.ui.assets_screen.detailed_screen.composables.PeriodChipGroup
import com.example.myportfolio.ui.compose.AppTheme
import com.example.myportfolio.ui.compose.previewCurrency
import com.example.myportfolio.ui.models.UICurrency
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun DetailedCurrencyScreen(
    assetId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailedAssetViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    viewModel.fetchAsset(assetId, mainViewModel.defaultCurrency)

    Surface {
        val asset by viewModel.asset.observeAsState()
        if (asset == null) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            DetailedCurrencyItemContent(
                period = viewModel.period,
                onPeriodPicked = { newPeriod ->
                    viewModel.changePeriod(newPeriod)
                },
                onCurrencyPicked = { newCurrency ->
                    viewModel.changeConvertToCurrency(newCurrency)
                },
                currency = asset as UICurrency,
                modifier = modifier
            )
        }
    }
}

@Composable
fun DetailedCurrencyItemContent(
    period: TimePeriod,
    onPeriodPicked: (TimePeriod) -> Unit,
    onCurrencyPicked: (CurrencyCode) -> Unit,
    currency: UICurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.average_padding))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = currency.domainAsset.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = currency.domainAsset.code.name,
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = stringResource(R.string.conversion_rate),
            fontSize = dimensionResource(R.dimen.average_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.small_padding),
                bottom = dimensionResource(R.dimen.tiny_padding)
            )
        )
        MoneyPickerRow(
            from = currency.domainAsset.code,
            onCurrencyPicked = onCurrencyPicked
        )
        PeriodChipGroup(
            pickedPeriod = period,
            onPeriodPicked = onPeriodPicked,
            listOfPeriods = TimePeriod.getPeriodsForDetailedCurrencyScreen(),
            modifier = Modifier.padding(
                top = dimensionResource(R.dimen.small_padding),
                bottom = dimensionResource(R.dimen.tiny_padding)
            )
        )
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    configureChart(context)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 222.dp),
            update = { chart ->
                chart.apply {
                    invalidate()
                    data = LineData(LineDataSet(currency.conversionRates, "Currency Rates"))
                    data.setValueTextColor(context.getColor(R.color.chart))
                    xAxis.valueFormatter = ChartDateFormatter
                }
            }
        )
    }
}

@Preview
@Composable
fun DetailedCurrencyItemPreview() {
    AppTheme {
        Surface {
            DetailedCurrencyItemContent(
                onPeriodPicked = {},
                onCurrencyPicked = {},
                period = TimePeriod.WEEK,
                currency = previewCurrency
            )
        }
    }
}
