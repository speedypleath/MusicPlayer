package com.example.androidmusicplayer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.example.androidmusicplayer.ui.component.SearchView
import com.example.androidmusicplayer.ui.component.SongList
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = getViewModel()
) {
    val songs by mainViewModel.songs.observeAsState()

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    songs?.data?.let {
    Column {
        SearchView(textState)
        SongList(songList = it, textState) { index ->
            mainViewModel.onClick(index)
        }
    }
    }
}