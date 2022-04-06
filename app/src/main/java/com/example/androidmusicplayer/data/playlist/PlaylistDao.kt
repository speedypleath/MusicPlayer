package com.example.androidmusicplayer.data.playlist

import androidx.room.*
import com.example.androidmusicplayer.model.Playlist

@Dao
interface PlaylistDao {
    @Insert
    fun insert(playlist: Playlist)

    @Update
    fun update(playlist: Playlist)

    @Delete
    fun delete(playlist: Playlist)

    @Query("SELECT * FROM playlist WHERE name = :playlist")
    fun getByName(playlist: String): Playlist
}