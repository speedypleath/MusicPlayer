package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.playlist.RoomPlaylist

@Dao
interface PlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: RoomPlaylist)

    @Update
    suspend fun update(playlist: RoomPlaylist)

    @Delete
    suspend fun delete(playlist: RoomPlaylist)

    @Query("SELECT * FROM playlist WHERE name = :playlist")
    suspend fun getByName(playlist: String): RoomPlaylist
}