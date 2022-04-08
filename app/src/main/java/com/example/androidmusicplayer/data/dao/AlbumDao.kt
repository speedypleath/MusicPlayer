package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.album.RoomAlbum

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: RoomAlbum)

    @Update
    fun update(album: RoomAlbum)

    @Delete
    fun delete(album: RoomAlbum)

    @Query("SELECT * FROM album WHERE name = :album")
    fun getByName(album: String): RoomAlbum

    @Query("SELECT * FROM album WHERE albumId = :album")
    fun getById(album: String): RoomAlbum
}