package com.example.androidmusicplayer.data.truth

import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.media.MediaItemTree
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MediaStoreDataSource(
    private val mediaStoreApi: MediaStoreApi,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchSongs(): MutableList<Song> =
        withContext(ioDispatcher) {
            if (mediaStoreApi.initialized) {
                val songs = mediaStoreApi.loadSongs()
                songs.map { song -> song.fromMediaStore() } as MutableList<Song>
            }
            else
                mutableListOf()
        }
}