package com.example.androidmusicplayer.ui.component

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun FullscreenPlayer(
    playerViewModel: PlayerViewModel = getViewModel()
) {
    val song by playerViewModel.song.observeAsState()
    val context = LocalContext.current
    Column {
        Text(text = "Fullscreen")
        song?.let { Text(it.title) }
        PlayerView(context).apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player = playerViewModel.player
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    }
}