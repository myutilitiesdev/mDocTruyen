package com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import doctruyenproject.composeapp.generated.resources.Res
import doctruyenproject.composeapp.generated.resources.app_name
import doctruyenproject.composeapp.generated.resources.story_detail

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        NavigationScreen.StoryDetail.route -> Res.string.story_detail.toString()
        else -> {
            Res.string.app_name.toString()
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBefore("/")
}