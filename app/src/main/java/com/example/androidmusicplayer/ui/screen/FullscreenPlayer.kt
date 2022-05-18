package com.example.androidmusicplayer.ui.component

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.ui.PlayerView
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun FullscreenPlayer(
    playerViewModel: PlayerViewModel = getViewModel()
) {
    val song by playerViewModel.song.observeAsState()
    val context = LocalContext.current
    song?.title?.let {
        Text(
            text = it,
            color = Color.White,
            modifier =
            Modifier.padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
    ConstraintLayout {
        val (title, videoPlayer) = createRefs()

        DisposableEffect(
            AndroidView(
                modifier =
                Modifier.testTag("VideoPlayer")
                    .constrainAs(videoPlayer) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                factory = {
                    PlayerView(context).apply {
                        player = playerViewModel.player
                        layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams
                                    .MATCH_PARENT,
                                ViewGroup.LayoutParams
                                    .MATCH_PARENT
                            )
                    }
                }
            )
        ) {
            onDispose { }
        }
    }
}

