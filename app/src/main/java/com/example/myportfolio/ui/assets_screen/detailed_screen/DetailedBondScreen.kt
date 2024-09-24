package com.example.myportfolio.ui.assets_screen.detailed_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myportfolio.Formatters
import com.example.myportfolio.R
import com.example.myportfolio.safeFormat
import com.example.myportfolio.ui.MainViewModel
import com.example.myportfolio.ui.compose.AppTheme
import com.example.myportfolio.ui.compose.previewBond
import com.example.myportfolio.ui.models.UIBond

@Composable
fun DetailedBondScreen(
    assetId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailedAssetViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    viewModel.fetchAsset(assetId, mainViewModel.defaultCurrency)
    val asset by viewModel.asset.observeAsState()

    if (asset == null) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        DetailedBondItemContent(
            asset as UIBond,
            modifier = modifier
        )
    }
}

@Composable
fun DetailedBondItemContent(
    bond: UIBond,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.average_padding))
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = bond.domainAsset.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = bond.domainAsset.code,
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = stringResource(R.string.info),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.small_padding))
        )
        Text(
            text = stringResource(R.string.value, bond.getPriceString(LocalContext.current)),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp
        )
        Text(
            text = stringResource(R.string.interest_rate, bond.domainAsset.rate),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp
        )
        Text(
            text = stringResource(R.string.issued_on),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.small_padding))
        )
        Text(
            text = bond.domainAsset.dateOfIssuance
                .safeFormat(Formatters.BOND_DETAILS_FORMATTER),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding))
        )
        Text(
            text = stringResource(R.string.maturity_date),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp
        )
        Text(
            text = bond.domainAsset.dateOfIssuance.plusYears(bond.domainAsset.yearsTillMaturity)
                .safeFormat(Formatters.BOND_DETAILS_FORMATTER),
            fontSize = dimensionResource(R.dimen.above_average_text_size).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.small_padding))
        )
    }
}

@Preview
@Composable
fun DetailedBondItemPreview() {
    AppTheme {
        Surface {
            DetailedBondItemContent(bond = previewBond)
        }
    }
}
