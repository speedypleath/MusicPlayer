package com.example.androidmusicplayer.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.example.androidmusicplayer.activity.CameraActivity
import com.example.androidmusicplayer.ui.component.IconButton
import com.example.androidmusicplayer.ui.viewmodel.SettingsViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Spotify
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.SdCard
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel()
) {
    val context = LocalContext.current
    val intent = Intent(context, CameraActivity::class.java)

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
        IconButton(
            onClick = { startActivity(context, intent, null) },
            icon = FontAwesomeIcons.Solid.Camera,
            text = "Open camera"
        )
    }
}