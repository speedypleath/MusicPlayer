package com.example.androidmusicplayer.activity

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.PlayerService
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.ui.App
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import com.example.androidmusicplayer.util.Status
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    val spotifyApi: SpotifyApi by inject { parametersOf(this) }
    val mediaStoreApi: MediaStoreApi by inject { parametersOf(this) }
    private val libraryViewModel: LibraryViewModel by viewModel()

    var activityResult: MutableLiveData<Status> = MutableLiveData(Status.LOADING)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Application Started!")
        if(!spotifyApi.isInitialized)
            spotifyApi.loadEndpoints("test-token")
        if(activityResult.value != Status.SUCCESS)
            mediaStoreApi.loadSongs()
        libraryViewModel.init(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        setContent {
            App()
        }
    }

    override fun onStop() {
        libraryViewModel.releaseBrowser()
        super.onStop()
    }

    override fun onBackPressed() {
        libraryViewModel.popPathStack()
    }
}