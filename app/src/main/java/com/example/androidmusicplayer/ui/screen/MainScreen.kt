package com.example.androidmusicplayer.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import com.example.androidmusicplayer.ui.component.SongList
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel()
) {
    mainViewModel.fetchSongs()
    val songs by mainViewModel.songs.observeAsState()
    songs?.data?.let {
        SongList(songList = it) {}
    }
}