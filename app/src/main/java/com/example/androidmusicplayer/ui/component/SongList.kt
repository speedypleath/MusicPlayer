package com.example.androidmusicplayer.ui.component


import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import com.example.androidmusicplayer.model.song.Song
import java.util.*

@Composable
fun SongList(
    songList: List<Song>,
    state: MutableState<TextFieldValue>,
    onClick: (Int) -> Unit,
) {
    Log.d("Song List", "Initialize song list")
    var filteredSongs: List<Song>
        LazyColumn {
            val searchedText = state.value.text
            filteredSongs = if (searchedText.isEmpty()) {
                songList
            } else {
                val resultList = mutableListOf<Song>()
                for (song in songList) {
                    if (song.title.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(song)
                    }
                }
                resultList
            }
            items(filteredSongs.size) { index ->
                SongTile(filteredSongs[index]) { onClick(index) }
            }
        }

}