package com.example.androidmusicplayer.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import com.example.androidmusicplayer.model.song.Song
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel()
) {
    mainViewModel.fetchSongs()
    val songs by mainViewModel.songs.observeAsState()
    songs?.data?.let { SongList(songList = it) }
}

@Composable
fun SongList(
    songList: List<Song>
) {
    Log.d("Song List", "Initialize song list")
    LazyColumn {
        items(songList.size) { index ->
            SongTile(songList[index])
        }
    }
}

@Composable
fun SongTile(
    song: Song
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
        imageModel = song.imageUri,
        modifier = Modifier.size(52.dp, 52.dp),
        success = {
            Image(
                bitmap = it.drawable!!.toBitmap().asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
            )
        },
        loading = {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val indicator = createRef()
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        },
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