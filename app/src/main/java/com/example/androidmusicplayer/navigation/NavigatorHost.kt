package com.example.androidmusicplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidmusicplayer.ui.screen.AppScreen
import com.example.androidmusicplayer.ui.screen.LibraryScreen
import com.example.androidmusicplayer.ui.screen.MainScreen
import com.example.androidmusicplayer.ui.screen.SettingsScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.name,
        modifier = modifier
    ) {

        composable(AppScreen.Home.name) {
            MainScreen()
        }
        composable(AppScreen.Library.name) {
            LibraryScreen()
        }
        composable(AppScreen.Settings.name) {
            SettingsScreen()
        }
    }
}