package com.example.myportfolio.ui.assets_screen.list_items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myportfolio.R
import com.example.myportfolio.ui.compose.previewCurrency
import com.example.myportfolio.ui.models.UICurrency

@Composable
fun CurrencyListItem(
    currency: UICurrency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(R.dimen.average_padding))
        ) {
            Text(
                text = currency.domainAsset.name,
                fontSize = dimensionResource(R.dimen.average_text_size).value.sp
            )
            Text(
                text = currency.domainAsset.code.name,
                fontSize = dimensionResource(R.dimen.small_text_size).value.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = currency.getPriceString(LocalContext.current),
            fontSize = dimensionResource(R.dimen.average_text_size).value.sp,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.average_padding))
        )
    }
}

@Preview
@Composable
fun CurrencyListItemPreview() {
    Surface {
        CurrencyListItem(
            currency = previewCurrency,
            onClick = {}
        )
    }
}
