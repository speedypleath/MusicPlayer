package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.artist.RoomArtist

@Dao
interface ArtistDao {
    @Insert
    fun insert(artist: RoomArtist)

    @Update
    fun update(artist: RoomArtist)

    @Delete
    fun delete(artist: RoomArtist)

    @Query("SELECT * FROM artist WHERE name = :artist")
    fun getByName(artist: String): RoomArtist?

    @Query("SELECT * FROM artist WHERE artistId = :artist")
    fun getById(artist: String): RoomArtist?
}