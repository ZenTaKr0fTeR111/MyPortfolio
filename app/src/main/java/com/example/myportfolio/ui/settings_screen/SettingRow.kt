package com.example.myportfolio.ui.settings_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myportfolio.R

@Composable
fun SettingRow(
    settingLabel: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.small_padding))
    ) {
        Text(
            text = settingLabel,
            fontSize = dimensionResource(R.dimen.settings_text_size).value.sp,
            modifier = Modifier.weight(1f)
        )
        content()
    }
}

@Preview
@Composable
fun SettingRowPreview() {
    Surface {
        SettingRow(settingLabel = "Some Setting") {
            Text(
                text = "1",
                fontSize = dimensionResource(R.dimen.settings_text_size).value.sp
            )
        }
    }
}
