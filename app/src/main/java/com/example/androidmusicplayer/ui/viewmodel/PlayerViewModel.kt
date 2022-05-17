package com.example.androidmusicplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture

class PlayerViewModel(
    private val controller: ListenableFuture<MediaController>
) : ViewModel() {
    fun init() {
        Log.d("Player View Model", "Init!")
    }
}