package com.example.androidmusicplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidmusicplayer.model.Playlist
import com.example.androidmusicplayer.data.playlist.PlaylistDao
import com.example.androidmusicplayer.data.playlist.PlaylistSongCrossRef
import com.example.androidmusicplayer.model.Song
import com.example.androidmusicplayer.data.song.SongDao

@Database(entities = [Song::class, Playlist::class, PlaylistSongCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
}
