package com.example.androidmusicplayer.ui.component


import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.androidmusicplayer.model.song.Song

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