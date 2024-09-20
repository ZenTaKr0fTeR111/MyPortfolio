package com.example.myportfolio.ui.portfolio_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myportfolio.ui.compose.AppTheme

@Composable
fun PortfolioScreen(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Portfolio Screen",
                fontSize = 30.sp
            )
        }
    }
}

@Preview
@Composable
fun PortfolioScreenPreview() {
    Surface {
        PortfolioScreen()
    }
}
