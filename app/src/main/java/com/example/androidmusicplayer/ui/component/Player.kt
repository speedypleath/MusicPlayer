package com.example.androidmusicplayer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidmusicplayer.ui.theme.White

@Preview
@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun Player() {
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
                    endY = 60f,
                ),
            ),
        backgroundColor = Color.Transparent,
        contentColor = White,
        elevation = 0.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

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
                progress = 0.4f,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .wrapContentHeight(Alignment.Bottom, true),

                backgroundColor = Color.Gray,
                color = Color.Green,
            )
        }
    }
}