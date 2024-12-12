package com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import doctruyenproject.composeapp.generated.resources.Res
import doctruyenproject.composeapp.generated.resources.app_name
import doctruyenproject.composeapp.generated.resources.tab_home
import doctruyenproject.composeapp.generated.resources.tab_hot
import doctruyenproject.composeapp.generated.resources.tab_profile
import org.jetbrains.compose.resources.StringResource

sealed class NavigationScreen(
    val route: String,
    val title: StringResource = Res.string.app_name,
    val navIcon: (@Composable (Color) -> Unit) = { tint: Color ->
        Icon(
            imageVector = Icons.Filled.Home, contentDescription = "home", tint = tint
        )
    },
    val objectName: String = "",
    val objectPath: String = ""
) {

    data object HomeScreen : NavigationScreen(route = "home_screen",
        title = Res.string.tab_home,
        navIcon = { tint: Color ->
            Icon(
                imageVector = Icons.Filled.Home, contentDescription = "home", tint = tint
            )
        })

    data object HotScreen : NavigationScreen(route = "hot_screen",
        title = Res.string.tab_hot,
        navIcon = { tint: Color ->
            Icon(
                imageVector = Icons.Filled.Favorite, contentDescription = "", tint = tint
            )
        })

    data object ProfileScreen : NavigationScreen(route = "profile_screen",
        title = Res.string.tab_profile,
        navIcon = { tint: Color ->
            Icon(
                imageVector = Icons.Filled.Person, contentDescription = "", tint = tint
            )
        })

    data object StoryDetail : NavigationScreen(
        "story_detail", objectName = "storyItem", objectPath = "/{storyItem}"
    )

    data object ReadStory : NavigationScreen(
        "read_story", objectName = "storyItem", objectPath = "/{storyItem}/{chapterItem}"
    )

    data object LoginScreen : NavigationScreen("login_screen")
    data object RegisterScreen : NavigationScreen("register_screen")
}