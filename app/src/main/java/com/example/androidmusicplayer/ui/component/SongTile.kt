package com.example.androidmusicplayer.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.androidmusicplayer.model.song.Song

@Composable
fun SongTile(
    song: Song
) {
    Log.d("Song", (song.image == null).toString())
    val painter: Painter = if(song.image == null)
        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(song.imageUri)
                .allowConversionToBitmap(true)
                .build(),
        )
    else
        BitmapPainter(song.image!!.asImageBitmap())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(52.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = song.title,
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp
            )
            song.artist?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.caption,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                )
            }
        }
    }
}