package com.example.androidmusicplayer.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.androidmusicplayer.data.song.CLIENT_ID
import com.example.androidmusicplayer.data.song.REDIRECT_URI
import com.example.androidmusicplayer.data.song.REQUEST_CODE
import com.example.androidmusicplayer.ui.theme.AndroidMusicPlayerTheme
import com.example.androidmusicplayer.util.Status
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel : MainViewModel by viewModel()
    private val spotifyApi = SpotifyApi()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val spotifyApi = SpotifyApi()
        spotifyApi.connect(applicationContext)
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Main activity", "Permission granted")
                }
                else {
                    Log.d("Main activity", "No permission")
                }
            }
        val builder = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )

        builder.setScopes(arrayOf("user-read-recently-played", "user-follow-read", "user-library-read", "playlist-read-private"))
        val request = builder.build()
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
        mainViewModel.fetchSongs(requestPermissionLauncher)
        mainViewModel.songs.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("Main activity", it.data.toString())
                }
                Status.LOADING -> {
                    Log.d("Main activity", "loading")
                }
                Status.ERROR -> {
                    Log.d("Main activity", "error")
                }
            }
        }

        setContent {
            AndroidMusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        spotifyApi.connect(this)
    }
}