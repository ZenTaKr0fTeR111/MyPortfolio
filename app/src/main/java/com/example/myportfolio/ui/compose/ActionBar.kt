package com.example.myportfolio.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myportfolio.R

@Composable
fun ActionBar(
    title: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val shouldNotShowUpButton =
        currentBackStackEntry?.destination?.route?.endsWith("MainRoute") ?: true

    ActionBarContent(
        title = title,
        shouldShowUpButton = !shouldNotShowUpButton,
        onUp = { navController.popBackStack() },
        modifier = modifier
    )
}

@Composable
fun ActionBarContent(
    title: String,
    shouldShowUpButton: Boolean,
    onUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.average_padding) * 2,
                    start = dimensionResource(R.dimen.average_padding),
                    end = dimensionResource(R.dimen.average_padding),
                    bottom = dimensionResource(R.dimen.small_padding)
                )
                .height(dimensionResource(R.dimen.app_bar_height))
        ) {
            if (shouldShowUpButton) {
                IconButton(onClick = onUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.average_padding))
                    )
                }
            }
            Text(
                text = title,
                fontSize = dimensionResource(R.dimen.top_bar_text_size).value.sp
            )
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    AppTheme {
        ActionBarContent(
            title = stringResource(R.string.app_name),
            shouldShowUpButton = true,
            onUp = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBarPreview() {
    AppTheme {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.app_name)
                )
            }
        )
    }
}
