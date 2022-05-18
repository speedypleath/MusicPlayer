package com.example.androidmusicplayer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidmusicplayer.ui.component.FullscreenPlayer
import com.example.androidmusicplayer.ui.screen.AppScreen
import com.example.androidmusicplayer.ui.screen.LibraryScreen
import com.example.androidmusicplayer.ui.screen.MainScreen
import com.example.androidmusicplayer.ui.screen.SettingsScreen
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.name,
        modifier = modifier
    ) {
        composable("song/$id") {
            FullscreenPlayer()
        }

        composable("playlist/{mediaId}") { backStackEntry ->
            val mainViewModel = getViewModel<MainViewModel>()
            mainViewModel.tree.updateChildrenList(backStackEntry.arguments?.getString("mediaId").toString())
            MainScreen()
        }

        composable(AppScreen.Home.name) {
            MainScreen()
        }
        composable(AppScreen.Library.name) {
            val mainViewModel = getViewModel<LibraryViewModel>()
            mainViewModel.tree.popAll()
            mainViewModel.tree.updateChildrenList("[rootID]")
            LibraryScreen(navController)
        }
        composable(AppScreen.Settings.name) {
            SettingsScreen()
        }
    }
}