package com.example.androidmusicplayer.ui

//import android.content.Context
//import androidx.media3.common.MediaItem
//import androidx.media3.datasource.DataSource
//import androidx.media3.datasource.DefaultDataSource
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.exoplayer.source.ProgressiveMediaSource
//
//class PlayerController(
//    private val context: Context
//) {
//
//    private val exoPlayer by lazy {
//        ExoPlayer.Builder(context).build()
//    }
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    fun prepare(sourceUrl: String) {
//        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(context)
//
//        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(MediaItem.fromUri(sourceUrl))
//
//        exoPlayer.prepare(source)
//    }
//
////    fun setPlayerView(playerView: PlayerView) {
////        playerView.player = exoPlayer
////    }
//
//}