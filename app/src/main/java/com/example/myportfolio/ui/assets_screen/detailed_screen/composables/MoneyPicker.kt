package com.example.myportfolio.ui.assets_screen.detailed_screen.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowForward
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myportfolio.R
import com.example.myportfolio.domain.models.CurrencyCode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyPicker(
    modifier: Modifier = Modifier,
    onCurrencyPicked: (CurrencyCode) -> Unit,
    enabled: Boolean = true,
    listOfCurrencyCodes: List<CurrencyCode> = CurrencyCode.entries,
    displayedCurrency: CurrencyCode = listOfCurrencyCodes[0]
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf(displayedCurrency) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { newExpanded -> expanded = newExpanded },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            value = selectedOption.name,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded && enabled
                )
            },
            enabled = enabled,
            modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
        )
        if (enabled) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                for (currencyCode in listOfCurrencyCodes) {
                    DropdownMenuItem(
                        text = {
                            Text(text = currencyCode.name)
                        },
                        onClick = {
                            onCurrencyPicked(currencyCode)
                            selectedOption = currencyCode
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MoneyPickerRow(
    from: CurrencyCode,
    onCurrencyPicked: (CurrencyCode) -> Unit,
    modifier: Modifier = Modifier,
    listOfCurrencyCodes: List<CurrencyCode> = CurrencyCode.entries
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        MoneyPicker(
            listOfCurrencyCodes = listOfCurrencyCodes,
            displayedCurrency = from,
            enabled = false,
            onCurrencyPicked = {},
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Sharp.ArrowForward,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.tiny_padding))
        )
        MoneyPicker(
            listOfCurrencyCodes = listOfCurrencyCodes,
            onCurrencyPicked = onCurrencyPicked,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun MoneyPickerPreview() {
    Surface {
        MoneyPicker(
            onCurrencyPicked = {},
            listOfCurrencyCodes = CurrencyCode.entries
        )
    }
}

@Preview
@Composable
fun MoneyPickerRowPreview() {
    Surface {
        MoneyPickerRow(
            onCurrencyPicked = {},
            from = CurrencyCode.USD
        )
    }
}
