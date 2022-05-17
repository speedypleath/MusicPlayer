package com.example.androidmusicplayer.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.androidmusicplayer.ui.component.IconButton
import com.example.androidmusicplayer.ui.viewmodel.SettingsViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Spotify
import compose.icons.fontawesomeicons.solid.SdCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel()
) {
    Column {
        IconButton(
            onClick = { viewModel.spotifyLogin() },
            icon = FontAwesomeIcons.Brands.Spotify,
            text = "Authenticate with Spotify"
        )
        IconButton(
            onClick = { viewModel.internalStorageRequest() },
            icon = FontAwesomeIcons.Solid.SdCard,
            text = "Internal storage permission"
        )
    }
}