package com.example.androidmusicplayer.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import com.example.androidmusicplayer.ui.component.SongList
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel()
) {
    val songs by mainViewModel.tree.subitems
        .observeAsState()
    val aux = songs?.map { song ->
        Song(
            song.mediaId,
            song.mediaMetadata.title.toString(),
            Artist(song.mediaMetadata.artist.toString(), song.mediaMetadata.artist.toString(), null, song.mediaMetadata.mediaUri.toString()),
            Album(song.mediaMetadata.artist.toString(), song.mediaMetadata.artist.toString(), null, song.mediaMetadata.mediaUri.toString()),
            0,
            song.mediaMetadata.artworkUri.toString(),
            null,
            song.mediaMetadata.mediaUri.toString()
        )
    }

    songs?.let {
        if (aux != null) {
            SongList(songList = aux) { index ->
                mainViewModel.onClick(index)
            }
        }
    }
}