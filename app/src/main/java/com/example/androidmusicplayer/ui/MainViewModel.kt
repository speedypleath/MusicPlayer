package com.example.androidmusicplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.util.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val songRepository: SongRepository,
    private val mediaStoreApi: MediaStoreApi
) : ViewModel() {

    private val _songs = MutableLiveData<Resource<List<Song>>>()
    val songs: LiveData<Resource<List<Song>>>
        get() = _songs

    fun fetchSongs() {
        viewModelScope.launch {
            _songs.postValue(Resource.loading(null))
            mediaStoreApi.requestPermission()
            songRepository.fetchSongs()
        }
    }
}