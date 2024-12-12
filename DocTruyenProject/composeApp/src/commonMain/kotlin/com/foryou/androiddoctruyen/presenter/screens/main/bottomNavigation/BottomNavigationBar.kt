package com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        val items = listOf(
            NavigationScreen.HomeScreen, NavigationScreen.HotScreen, NavigationScreen.ProfileScreen
        )

        items.forEach { item ->
            NavigationBarItem(
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(stringResource(item.title)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Black
                ),
                icon = { item.navIcon(if (currentRoute == item.route) Color.White else Color.Black) },
                selected = currentRoute == item.route
            )
        }
    }
}