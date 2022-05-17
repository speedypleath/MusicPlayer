package com.example.androidmusicplayer.ui.component

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Playlist(
    playlistViewModel: PlaylistViewModel = getViewModel()
) {
    val subitems by playlistViewModel.subitems.observeAsState()
    Log.d("Song List", "Initialize song list")
    subitems.let { items ->
        LazyColumn {
            if(items == null)
                return@LazyColumn
            items(items.size) { index ->
                val song = Song(
                    items[index].mediaId,
                    items[index].mediaMetadata.title.toString(),
                    null,
                    null,
                    0,
                    null,
                    null,
                    null
                )
                SongTile(song) { playlistViewModel.onClick(index) }
            }
        }
    }
}