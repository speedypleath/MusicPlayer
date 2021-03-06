package com.example.androidmusicplayer.ui.component

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun Player(
    playerViewModel: PlayerViewModel = getViewModel()
) {
    var title = "No activity!"
    var artist = ""

    val song by playerViewModel.song.observeAsState()
    val duration by playerViewModel.duration.observeAsState(initial = -1)
    Log.d("PlayerView", "length: $duration")
    if(song != null && song?.title != title) {
        playerViewModel.player?.prepare()
        title = song!!.title
        artist = song!!.artist!!.name
    }

    val isPlaying = remember { mutableStateOf(false) }
    val icon = if(isPlaying.value)
        Icons.Default.Add
    else
        Icons.Default.PlayArrow

    BottomAppBar(
        modifier = Modifier
            .height(78.dp)
            .fillMaxWidth()
            .absolutePadding(left = 16.dp, right = 16.dp)
            .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.DarkGray.copy(alpha = 0.8f),
                        Color.Black,
                    ),
                    endY = 40f,
                ),
            ),
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(title)
                    Text(artist)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxSize(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        "Play/Pause",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(32.dp)
                            .fillMaxSize(1f)
                            .scale(1.2f)
                            .clickable {
                                isPlaying.value = !isPlaying.value
                            }
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        "Repeat",
                        modifier = Modifier
                            .padding(end = 14.dp)
                            .size(32.dp)
                            .fillMaxSize(1f)
                    )
                }

            }

            LinearProgressIndicator(
                progress = 0.5f,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .wrapContentHeight(Alignment.Bottom, true)
            )
        }
    }
}