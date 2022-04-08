package com.example.androidmusicplayer.data.repository

import android.content.Context
import androidx.room.Room
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.dao.SongDao
import com.example.androidmusicplayer.data.mediastore.MediaStoreDataSource
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SongRepository(
    private val mediaStoreDataSource: MediaStoreDataSource
) {
    private lateinit var songDao: SongDao
    private lateinit var db: AppDatabase
    fun createDb(context: Context) {
        db = Room.inMemoryDatabaseBuilder(
            context.applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        songDao = db.songDao()
    }
    private val mutex = Mutex()
    private var songs: List<Song> = emptyList()

    suspend fun fetchSongs(refresh: Boolean = false): List<Song> {
        if (refresh || songs.isEmpty()) {
            val songResult = mediaStoreDataSource.fetchSongs()
            mutex.withLock {
                this.songs = songResult
            }
        }

        return mutex.withLock { this.songs }
    }
}