package com.foryou.androiddoctruyen.presenter.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.BottomNavigationBar
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.NavigationHost
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.NavigationScreen
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.currentRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = {},
            bottomBar = {
                when (currentRoute(navController)) {
                    NavigationScreen.HomeScreen.route, NavigationScreen.HotScreen.route, NavigationScreen.ProfileScreen.route -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            BottomNavigationBar(navController)
                        }
                    }
                }
            }
        ) {
            NavigationHost(navController)
        }
    }
}