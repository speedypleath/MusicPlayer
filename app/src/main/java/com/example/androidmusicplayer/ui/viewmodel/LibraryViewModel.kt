package com.example.androidmusicplayer.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.media.PlayerService
import com.example.androidmusicplayer.ui.state.TreePathState
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class LibraryViewModel(
    val tree: TreePathState
): ViewModel() {

}