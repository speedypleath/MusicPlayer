package com.example.androidmusicplayer.ui

import android.content.Context
import android.util.Log
import com.example.androidmusicplayer.data.song.CLIENT_ID
import com.example.androidmusicplayer.data.song.REDIRECT_URI
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.client.CallResult
import com.spotify.protocol.types.Empty
import com.spotify.protocol.types.Track

class SpotifyApi {
    enum class PlayingState {
        PAUSED, PLAYING, STOPPED
    }

    private var mSpotifyAppRemote: SpotifyAppRemote? = null
    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context) {
        if (mSpotifyAppRemote?.isConnected == true) {
            return
        }
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote
                Log.d("SpotifyService", "Connected!")
            }
            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyService", throwable.message, throwable)
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }

    fun play(uri: String): CallResult<Empty>? {
        return mSpotifyAppRemote?.playerApi?.play(uri)
    }

    fun resume() {
        mSpotifyAppRemote?.playerApi?.resume()
    }

    fun pause() {
        mSpotifyAppRemote?.playerApi?.pause()
    }

    fun playingState(handler: (PlayingState) -> Unit) {
        mSpotifyAppRemote?.playerApi?.playerState?.setResultCallback { result ->
            when {
                result.track.uri == null -> {
                    handler(PlayingState.STOPPED)
                }
                result.isPaused -> {
                    handler(PlayingState.PAUSED)
                }
                else -> {
                    handler(PlayingState.PLAYING)
                }
            }
        }
    }

    fun disconnect() {
        Log.d("SpotifyService", "Disconnected!")
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }

    fun subscribeToTrackPosition(handler: (Long) -> Unit) {
        mSpotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback { result ->
            handler(result.playbackPosition)
        }
    }

    fun subscribeToTrack(handler: (Track) -> Unit) {
        mSpotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            handler(it.track)
        }
    }
}