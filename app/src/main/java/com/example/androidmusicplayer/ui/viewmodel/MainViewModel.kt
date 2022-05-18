package com.example.androidmusicplayer.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.media.SpotifyPlayer
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.ui.state.TreePathState
import com.example.androidmusicplayer.util.Resource
import com.example.androidmusicplayer.workers.LoadBitmapsWorker
import com.example.androidmusicplayer.workers.LoadSongsWorker
import kotlinx.coroutines.launch

class MainViewModel (
    private val songRepository: SongRepository,
    val tree: TreePathState
) : ViewModel() {
    private val _songs = MutableLiveData<Resource<List<Song>>>()
    private val spotifyPlayer = SpotifyPlayer()
    val songs: LiveData<Resource<List<Song>>>
        get() = _songs

    fun fetchSongs(context: Context) {
        spotifyPlayer.connect(context)
        val loadSongsWork: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<LoadSongsWorker>()
                .build()

        val loadBitmapWork: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<LoadBitmapsWorker>()
                .build()

        WorkManager
            .getInstance(context)
            .beginWith(loadSongsWork)
            .then(loadBitmapWork)
            .enqueue()

        fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            _songs.postValue(Resource.loading(null))
            _songs.postValue(Resource.success(songRepository.fetchSongs()))
        }
    }

    fun updateView() {
        fetch()
        val newTitles = tree.subitems.value!!.map { song -> song.mediaMetadata.title }
        val newSongs = songs.value!!.data!!.filter { song ->
            song.title in newTitles
        }
        _songs.postValue(Resource.success(newSongs))
    }

    fun onClick(position: Int) = run {
        val browser = tree.browser ?: return@run
        val song = songRepository.getSong(tree.subitems.value!![position].mediaMetadata.title.toString())
        if (song != null) {
            song.uri?.let { spotifyPlayer.play(it) }
        }
        browser.setMediaItems(tree.subitems.value!!)
        browser.shuffleModeEnabled = false
        browser.prepare()
        browser.seekToDefaultPosition(position)
        browser.play()
    }
}