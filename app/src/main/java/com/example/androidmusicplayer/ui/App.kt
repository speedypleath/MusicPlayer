package com.example.androidmusicplayer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidmusicplayer.ui.component.Player
import com.example.androidmusicplayer.ui.navigation.NavigationBar
import com.example.androidmusicplayer.ui.navigation.NavigationHost
import com.example.androidmusicplayer.ui.screen.AppScreen
import com.example.androidmusicplayer.ui.theme.AndroidMusicPlayerTheme

@Composable
fun App() {
    val allScreens = AppScreen.values().toList()
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.fromRoute(backstackEntry.value?.destination?.route)

    AndroidMusicPlayerTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Column{
                    Player()
                    NavigationBar(
                        allScreens = allScreens,
                        onTabSelected = { screen ->
                            navController.navigate(screen.name)
                        },
                        currentScreen = currentScreen,
                    )
                }
            },
            content = { NavigationHost(navController) }
        )
    }
}
