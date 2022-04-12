package com.example.androidmusicplayer.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.util.Status
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : ComponentActivity() {
    private val repository: SongRepository by inject()
    private val spotifyApi: SpotifyApi by inject { parametersOf(this) }
    private val mediaStoreApi: MediaStoreApi by inject { parametersOf(this) }
    private val mainViewModel: MainViewModel by viewModel()
    var activityResult: MutableLiveData<Status> = MutableLiveData(Status.LOADING)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Application Started!")
        if(!spotifyApi.initialized)
            spotifyApi.requestPermission()
        if(!mediaStoreApi.initialized)
            mediaStoreApi.requestPermission()
    }
}