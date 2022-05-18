package com.example.androidmusicplayer.activity

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.ui.App
import com.example.androidmusicplayer.ui.component.MainContent
import com.example.androidmusicplayer.ui.state.TreePathState
import com.example.androidmusicplayer.ui.viewmodel.LibraryViewModel
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import com.example.androidmusicplayer.util.Status
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    lateinit var spotifyApi: SpotifyApi
    lateinit var mediaStoreApi: MediaStoreApi
    private val playerViewModel: PlayerViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val treePathState: TreePathState by inject()

    var activityResult: MutableLiveData<Status> = MutableLiveData(Status.LOADING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Application Started!")
        spotifyApi = get { parametersOf(this) }
        mediaStoreApi = get { parametersOf(this) }
        playerViewModel.init(applicationContext)
        mainViewModel.fetchSongs()
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onStart() {
        super.onStart()
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                MainContent(Modifier.fillMaxSize())
            }
        }
    }

    override fun onStop() {
        playerViewModel.releaseController()
        treePathState.releaseBrowser()
        super.onStop()
    }
}