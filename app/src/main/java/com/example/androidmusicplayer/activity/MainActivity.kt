package com.example.androidmusicplayer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.MutableLiveData
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.ui.App
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import com.example.androidmusicplayer.util.Status
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    lateinit var spotifyApi: SpotifyApi
    lateinit var mediaStoreApi: MediaStoreApi
    private val playerViewModel: PlayerViewModel by viewModel()
    private val libraryViewModel: LibraryViewModel by viewModel()

    var activityResult: MutableLiveData<Status> = MutableLiveData(Status.LOADING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Application Started!")
        spotifyApi = get { parametersOf(this) }
        mediaStoreApi = get { parametersOf(this) }
        playerViewModel.init(applicationContext)
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