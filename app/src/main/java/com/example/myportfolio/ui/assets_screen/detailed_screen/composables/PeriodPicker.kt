package com.example.myportfolio.ui.assets_screen.detailed_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.TimePeriod

@Composable
fun PeriodChipGroup(
    pickedPeriod: TimePeriod,
    onPeriodPicked: (TimePeriod) -> Unit,
    modifier: Modifier = Modifier,
    listOfPeriods: List<TimePeriod> = TimePeriod.getPeriodsForDetailedCurrencyScreen()
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.tiny_padding)),
        modifier = modifier
    ) {
        listOfPeriods.forEach { period ->
            FilterChip(
                selected = period == pickedPeriod,
                onClick = { onPeriodPicked(period) },
                label = { Text(text = period.name) }
            )
        }
    }
}

@Preview
@Composable
fun PeriodChipGroupPreview() {
    Surface {
        PeriodChipGroup(
            onPeriodPicked = {},
            pickedPeriod = TimePeriod.WEEK
        )
    }
}
