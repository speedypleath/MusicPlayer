package com.example.androidmusicplayer.data.mediastore

import com.example.androidmusicplayer.model.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MediaStoreDataSource(
    private val mediaStoreApi: MediaStoreApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchSongs(): List<Song> =
        withContext(ioDispatcher) {
            mediaStoreApi.loadSongs()
        }
}