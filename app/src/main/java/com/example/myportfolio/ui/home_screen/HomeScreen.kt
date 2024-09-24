package com.example.myportfolio.ui.home_screen

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
fun HomeScreen(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Home Screen",
                fontSize = 30.sp
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Surface {
        HomeScreen()
    }
}
