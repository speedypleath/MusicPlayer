package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.playlist.RoomPlaylist

@Dao
interface PlaylistDao {
    @Insert
    fun insert(playlist: RoomPlaylist)

    @Update
    fun update(playlist: RoomPlaylist)

    @Delete
    fun delete(playlist: RoomPlaylist)

    @Query("SELECT * FROM playlist WHERE name = :playlist")
    fun getByName(playlist: String): RoomPlaylist
}