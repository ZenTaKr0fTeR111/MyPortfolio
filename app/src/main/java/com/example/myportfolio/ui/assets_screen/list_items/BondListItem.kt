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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myportfolio.R
import com.example.myportfolio.ui.compose.previewBond
import com.example.myportfolio.ui.models.UIBond

@Composable
fun BondListItem(
    bond: UIBond,
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
                text = bond.domainAsset.name,
                fontSize = dimensionResource(R.dimen.average_text_size).value.sp
            )
            Text(
                text = bond.domainAsset.code,
                fontSize = dimensionResource(R.dimen.small_text_size).value.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.average_padding))
        ) {
            Text(
                text = bond.getPriceString(LocalContext.current),
                fontSize = dimensionResource(R.dimen.average_text_size).value.sp
            )
            Text(
                text = stringResource(R.string.rate, bond.domainAsset.rate),
                fontSize = dimensionResource(R.dimen.small_padding).value.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun BondListItemPreview() {
    Surface {
        BondListItem(
            bond = previewBond,
            onClick = {}
        )
    }
}
