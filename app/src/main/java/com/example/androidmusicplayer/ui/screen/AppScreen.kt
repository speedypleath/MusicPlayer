package com.example.androidmusicplayer.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppScreen(
    val icon: ImageVector,
) {
    Home(
        icon = Icons.Filled.Home,
    ),
    Library(
        icon = Icons.Filled.Search,
    ),
    Settings(
        icon = Icons.Filled.Person,
    );

    companion object {
        fun fromRoute(route: String?): AppScreen =
            when (route?.substringBefore("/")) {
                Home.name -> Home
                Library.name -> Library
                Settings.name -> Settings
                null -> Home
                else -> Home
            }
    }
}