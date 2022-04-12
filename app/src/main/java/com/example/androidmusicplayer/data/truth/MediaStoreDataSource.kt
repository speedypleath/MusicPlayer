package com.example.androidmusicplayer.data.truth

import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MediaStoreDataSource(
    private val mediaStoreApi: MediaStoreApi,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchSongs(): MutableList<Song> =
        withContext(ioDispatcher) {
            mediaStoreApi.loadSongs().map { song -> song.fromMediaStore() } as MutableList<Song>
        }
}