package com.example.androidmusicplayer.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.media.PlayerService
import com.example.androidmusicplayer.model.song.Song
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: SongRepository
): ViewModel() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null
    val player: Player?
        get() = controller
    private val _song =  MutableLiveData<Song>()
    val song: LiveData<Song>
        get() = _song

    fun init(context: Context) {
        controllerFuture =
            MediaController.Builder(
                context,
                SessionToken(context, ComponentName(context, PlayerService::class.java))
            ).buildAsync()

        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun setController() {
        val controller = this.controller ?: return

        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    updateMediaMetadataUI(mediaItem?.mediaMetadata ?: MediaMetadata.EMPTY)
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                    updateShuffleSwitchUI(shuffleModeEnabled)
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                    updateRepeatSwitchUI(repeatMode)
                }
            }
        )
    }

    private fun updateShuffleSwitchUI(shuffleModeEnabled: Boolean) {

    }

    private fun updateRepeatSwitchUI(repeatMode: Int) {

    }

    private fun updateMediaMetadataUI(mediaMetadata: MediaMetadata) {

    }

    fun releaseController() {
        MediaController.releaseFuture(controllerFuture)
    }
}