package com.example.androidmusicplayer.data.repository

import android.content.Context
import androidx.recyclerview.widget.ListAdapter
import androidx.room.Room
import com.example.androidmusicplayer.MediaItemTree
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.util.Status
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SongRepository(
    private val mediaStoreDataSource: MediaStoreDataSource,
    private val spotifyDataSource: SpotifyDataSource
) {
    private lateinit var db: AppDatabase

    fun createDb(context: Context) {
        db = Room.inMemoryDatabaseBuilder(
            context.applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    private val mutex = Mutex()
    var songs: List<Song> = emptyList<Song>().toMutableList()

    suspend fun fetchSongs(refresh: Boolean = false): List<Song> {
        if (refresh || songs.isEmpty()) {
            val songResult = mediaStoreDataSource.fetchSongs() + spotifyDataSource.fetchSongs()

            val newResults = songResult - this.songs.toSet()
            for(song: Song in newResults)
                MediaItemTree.addNodeToTree(song)

            mutex.withLock {
                this.songs = songResult
            }
        }

        return mutex.withLock { this.songs }
    }
}
