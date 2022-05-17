package com.example.androidmusicplayer.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.example.androidmusicplayer.PlayerService
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class PlayerActivity : ComponentActivity() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null
    private val subItemMediaList: MutableList<MediaItem> = mutableListOf()
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(applicationContext).build()

        playerView = PlayerView(applicationContext).apply {
            layoutParams =
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams
                        .MATCH_PARENT,
                    ViewGroup.LayoutParams
                        .MATCH_PARENT
                )
        }
        setContent {
            playerView
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Player Activity", "Start!")
        initializeController()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        releaseController()
    }

    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                this,
                SessionToken(this, ComponentName(this, PlayerService::class.java))
            )
                .buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
        val playerView: PlayerViewModel = getViewModel { parametersOf(controllerFuture) }
        playerView.init()
    }

    private fun releaseController() {
        MediaController.releaseFuture(controllerFuture)
    }


    private fun setController() {
        val controller = this.controller ?: return

        playerView.player = controller
//
        updateCurrentPlaylist()
//        updateMediaMetadataUI(controller.mediaMetadata)
//        updateShuffleSwitchUI(controller.shuffleModeEnabled)
//        updateRepeatSwitchUI(controller.repeatMode)

        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
//                    updateMediaMetadataUI(mediaItem?.mediaMetadata ?: MediaMetadata.EMPTY)
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//                    updateShuffleSwitchUI(shuffleModeEnabled)
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
//                    updateRepeatSwitchUI(repeatMode)
                }
            }
        )
    }

    private fun updateCurrentPlaylist() {
        val controller = this.controller ?: return
        subItemMediaList.clear()
        for (i in 0 until controller.mediaItemCount) {
            subItemMediaList.add(controller.getMediaItemAt(i))
        }
    }
}