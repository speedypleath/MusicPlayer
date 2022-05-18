package com.example.androidmusicplayer.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.media.MediaItemTree
import com.example.androidmusicplayer.media.PlayerService
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.ui.state.TreePathState
import com.example.androidmusicplayer.util.Resource
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel (
    private val songRepository: SongRepository,
    val tree: TreePathState
) : ViewModel() {
    private val _songs = MutableLiveData<Resource<List<Song>>>()
    val songs: LiveData<Resource<List<Song>>>
        get() = _songs

    fun fetchSongs() {
        viewModelScope.launch {
            _songs.postValue(Resource.loading(null))
            _songs.postValue(Resource.success(songRepository.fetchSongs()))
        }
    }

    fun onClick(position: Int) = run {
        val browser = tree.browser ?: return@run
        browser.setMediaItems(tree.subitems.value!!)
        browser.shuffleModeEnabled = false
        browser.prepare()
        browser.seekToDefaultPosition(position)
        browser.play()
    }
}