package com.example.androidmusicplayer.ui.viewmodel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.PlayerService
import com.example.androidmusicplayer.activity.PlaylistActivity
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class PlaylistViewModel: ViewModel() {
    private lateinit var browserFuture: ListenableFuture<MediaBrowser>
    private val browser: MediaBrowser?
        get() = if (browserFuture.isDone) browserFuture.get() else null
    private val _subitems = MutableLiveData<List<MediaItem>>()
    val subitems: LiveData<List<MediaItem>>
        get() = _subitems

    fun init(context: Context, intent: Intent) {
        browserFuture =
            MediaBrowser.Builder(
                context,
                SessionToken(context, ComponentName(context, PlayerService::class.java))
            )
                .buildAsync()
        browserFuture.addListener({ displayFolder(intent) }, MoreExecutors.directExecutor())
    }

    fun onClick(position: Int) = run {
        val browser = this.browser ?: return@run
        browser.setMediaItems(subitems.value!!)
        browser.shuffleModeEnabled = false
        browser.prepare()
        browser.seekToDefaultPosition(position)
        browser.play()
    }

    fun releaseBrowser() {
        MediaBrowser.releaseFuture(browserFuture)
    }

    private fun displayFolder(intent: Intent) {
        val browser = this.browser ?: return
        val id: String = intent.getStringExtra(PlaylistActivity.MEDIA_ITEM_ID_KEY)!!
        val mediaItemFuture = browser.getItem(id)
        val childrenFuture = browser.getChildren(id,0, Int.MAX_VALUE, null)
        mediaItemFuture.addListener(
            {
                val result = mediaItemFuture.get()
            },
            MoreExecutors.directExecutor()
        )
        childrenFuture.addListener(
            {
                val result = childrenFuture.get()
                val children = result.value!!

                _subitems.postValue(children)
            },
            MoreExecutors.directExecutor()
        )
    }
}