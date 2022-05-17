package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.album.RoomAlbum

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: RoomAlbum)

    @Update
    suspend fun update(album: RoomAlbum)

    @Delete
    suspend fun delete(album: RoomAlbum)

    @Query("SELECT * FROM album WHERE name = :album")
    suspend fun getByName(album: String): RoomAlbum

    @Query("SELECT * FROM album WHERE albumId = :album")
    suspend fun getById(album: String): RoomAlbum
}