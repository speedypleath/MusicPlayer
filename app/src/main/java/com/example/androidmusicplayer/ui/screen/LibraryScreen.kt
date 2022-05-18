package com.example.androidmusicplayer.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController
) {
    val libraryViewModel: LibraryViewModel = getViewModel()
    val subitems by libraryViewModel.tree.subitems.observeAsState()

    subitems.let { items ->
        LazyColumn {
            if(items == null)
                return@LazyColumn
            items(items.size) { index ->
                Button(onClick = {
                    libraryViewModel.tree.pushPathStack(items[index])
                    if (items[index].mediaMetadata.isPlayable == true) {
                        navController.navigate("playlist/${items[index].mediaId}")
                    }
                }) {
                    Text(text = items[index].mediaMetadata.title.toString())
                }
            }
        }
    }
}