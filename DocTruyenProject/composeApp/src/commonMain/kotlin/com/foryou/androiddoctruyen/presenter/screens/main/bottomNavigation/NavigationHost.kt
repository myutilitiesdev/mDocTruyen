package com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.foryou.androiddoctruyen.presenter.screens.main.tabs.HomeScreen
import com.foryou.androiddoctruyen.presenter.screens.main.tabs.HotScreen
import com.foryou.androiddoctruyen.presenter.screens.main.tabs.ProfileScreen

@Composable
fun NavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationScreen.HomeScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = NavigationScreen.HomeScreen.route) {
            HomeScreen()
        }

        composable(route = NavigationScreen.HotScreen.route) {
            HotScreen()
        }

        composable(route = NavigationScreen.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}