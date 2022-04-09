package com.example.androidmusicplayer.data.truth

import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MediaStoreDataSource(
    private val mediaStoreApi: MediaStoreApi,
    private val ioDispatcher: CoroutineDispatcher,
    private val songAdapter: SongAdapter
) {
    suspend fun fetchSongs(): MutableList<Song?> =
        withContext(ioDispatcher) {
            mediaStoreApi.loadSongs().map { song -> songAdapter.mediaStoreToSong(song) } as MutableList<Song?>
        }
}