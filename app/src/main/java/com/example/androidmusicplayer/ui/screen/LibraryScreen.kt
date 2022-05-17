package com.example.androidmusicplayer.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.example.androidmusicplayer.activity.PlaylistActivity
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = getViewModel()
) {
    val subitems by libraryViewModel.subitems.observeAsState()
    val context = LocalContext.current
    subitems.let { items ->
        LazyColumn {
            if(items == null)
                return@LazyColumn
            items(items.size) { index ->
                Button(onClick = {
                    if (items[index].mediaMetadata.isPlayable == true) {
                        val intent = PlaylistActivity.createIntent(context, items[index].mediaId)
                        startActivity(context, intent, null)
                    } else {
                        libraryViewModel.pushPathStack(items[index])
                    }
                }) {
                    Text(text = items[index].mediaMetadata.title.toString())
                }
            }
        }
    }
}