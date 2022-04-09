package com.example.androidmusicplayer.data.repository

import android.content.Context
import androidx.room.Room
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SongRepository(
    private val mediaStoreDataSource: MediaStoreDataSource,
    private val spotifyDataSource: SpotifyDataSource,
) {
    private lateinit var db: AppDatabase
    fun createDb(context: Context) {
        db = Room.inMemoryDatabaseBuilder(
            context.applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    private val mutex = Mutex()
    private var songs: MutableList<Song?> = emptyList<Song?>().toMutableList()

    suspend fun fetchSongs(refresh: Boolean = false): MutableList<Song?> {
        if (refresh || songs.isEmpty()) {
            val songResult = mediaStoreDataSource.fetchSongs() + spotifyDataSource.fetchSongs()
            mutex.withLock {
                this.songs = songResult as MutableList<Song?>
            }
        }

        return mutex.withLock { this.songs }
    }
}