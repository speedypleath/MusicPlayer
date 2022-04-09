package com.example.androidmusicplayer.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.ui.theme.AndroidMusicPlayerTheme
import com.example.androidmusicplayer.util.CLIENT_ID
import com.example.androidmusicplayer.util.REDIRECT_URI
import com.example.androidmusicplayer.util.SPOTIFY_TOKEN
import com.example.androidmusicplayer.util.Status
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val mediaStoreApi: MediaStoreApi by inject()
    private val dataStore: DataStore<Preferences> by inject()
    var activityResult: MutableLiveData<Status> = MutableLiveData(Status.LOADING)

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                activityResult = if (isGranted) {
                    Log.d("Main activity", "Permission granted")
                    MutableLiveData(Status.SUCCESS)
                } else {
                    Log.d("Main activity", "No permission")
                    MutableLiveData(Status.ERROR)
                }
            Log.d("Main activity", "Status: ${activityResult.value.toString()}")
        }

    private val request = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        ).setScopes(
            arrayOf(
                "user-read-recently-played",
                "user-follow-read",
                "user-library-read",
                "playlist-read-private"
            )
        ).build()

    private val spotifyAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d("Main activity", "Spotify login result: $activityResult")
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val token = AuthorizationClient.getResponse(activityResult.resultCode, activityResult.data).accessToken
                runBlocking {
                    dataStore.edit {
                        it[SPOTIFY_TOKEN] = token
                    }
                }
            }
        }

    fun connectToSpotify() {
        val spotifyIntent = AuthorizationClient.createLoginActivityIntent(this, request)
        spotifyAuthLauncher.launch(spotifyIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Application Started!")
        mediaStoreApi.registerLauncher(requestPermissionLauncher)
        connectToSpotify()

        setContent {
            AndroidMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}