package com.example.androidmusicplayer.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val ColorPalette = lightColors(
    primary = Green300,
    primaryVariant = Purple700,
    secondary = Teal200,
)

@Composable
fun AndroidMusicPlayerTheme(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = White
    )
    systemUiController.setNavigationBarColor(
        color = White
    )
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}