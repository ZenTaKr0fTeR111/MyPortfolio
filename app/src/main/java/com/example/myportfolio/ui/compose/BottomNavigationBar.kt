package com.example.myportfolio.ui.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    menuItems: List<NavigationBarItem> = defaultMenuItems
) {
    BottomNavigationBarContent(
        onClick = { navBarItem -> navController.navigate(navBarItem.route) { popUpTo(0) } },
        modifier = modifier,
        menuItems = menuItems
    )
}

@Composable
fun BottomNavigationBarContent(
    onClick: (NavigationBarItem) -> Unit,
    modifier: Modifier = Modifier,
    menuItems: List<NavigationBarItem> = defaultMenuItems
) {
    var itemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        for (i in menuItems) {
            NavigationBarItem(
                selected = itemIndex == menuItems.indexOf(i),
                onClick = {
                    itemIndex = menuItems.indexOf(i)
                    onClick(i)
                },
                label = {
                    Text(
                        text = stringResource(i.descriptionRes)
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(i.iconRes),
                        contentDescription = stringResource(i.descriptionRes)
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview(modifier: Modifier = Modifier) {
    AppTheme {
        BottomNavigationBarContent(
            onClick = {}
        )
    }
}
