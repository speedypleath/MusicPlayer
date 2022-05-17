package com.example.androidmusicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository

class SettingsViewModel(
    private val mediaStore: MediaStoreApi,
    private val spotify: SpotifyApi,
) : ViewModel() {
    fun spotifyLogin() = spotify.requestPermission()
    fun internalStorageRequest() = mediaStore.requestPermission()
}
