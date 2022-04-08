package com.example.androidmusicplayer.data.mediastore

import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.web.adapters.SongAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MediaStoreDataSource(
    private val mediaStoreApi: MediaStoreApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val songAdapter: SongAdapter
) {
    suspend fun fetchSongs(): MutableList<Song> =
        withContext(ioDispatcher) {
            mediaStoreApi.loadSongs().map { song -> songAdapter.mediaStoreToSong(song) } as MutableList<Song>
        }
}