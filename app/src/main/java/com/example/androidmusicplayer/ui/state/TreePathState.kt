package com.example.androidmusicplayer.ui.state

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.example.androidmusicplayer.media.PlayerService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class TreePathState {
    private lateinit var browserFuture: ListenableFuture<MediaBrowser>
    val browser: MediaBrowser?
        get() = if (browserFuture.isDone) browserFuture.get() else null
    private val treePathStack: ArrayDeque<MediaItem> = ArrayDeque()
    private val _subitems = MutableLiveData<List<MediaItem>>()
    val subitems: LiveData<List<MediaItem>>
        get() = _subitems

    fun init(context: Context) {
        browserFuture =
            MediaBrowser.Builder(
                context,
                SessionToken(context, ComponentName(context, PlayerService::class.java))
            )
                .buildAsync()
        browserFuture.addListener({ pushRoot() }, MoreExecutors.directExecutor())
    }

    private fun pushRoot() {
        if (!treePathStack.isEmpty()) {
            return
        }
        val browser = this.browser ?: return
        val rootFuture = browser.getLibraryRoot(null)
        rootFuture.addListener(
            {
                val result: LibraryResult<MediaItem> = rootFuture.get()
                val root: MediaItem = result.value!!
                pushPathStack(root)
            },
            MoreExecutors.directExecutor()
        )
    }

    fun pushPathStack(mediaItem: MediaItem) {
        treePathStack.addLast(mediaItem)
        updateChildrenList(treePathStack.last().mediaId)
    }

    private fun popPathStack() {
        treePathStack.removeLast()
        if (treePathStack.size == 0) {
            return
        }

        updateChildrenList(treePathStack.last().mediaId)
    }

    fun updateChildrenList(mediaId: String) {
        val browser = this.browser ?: return

        val childrenFuture =
            browser.getChildren(
                mediaId,
                0,
                Int.MAX_VALUE,
                null
            )

        _subitems.postValue(listOf())
        childrenFuture.addListener(
            {
                val result = childrenFuture.get()
                val children = result.value!!
                _subitems.postValue(children)
            },
            MoreExecutors.directExecutor()
        )
    }

    fun releaseBrowser() {
        MediaBrowser.releaseFuture(browserFuture)
    }

    fun popAll() {
        while (treePathStack.size > 0)
            popPathStack()
    }
}