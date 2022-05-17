package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.artist.RoomArtist

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artist: RoomArtist)

    @Update
    suspend fun update(artist: RoomArtist)

    @Delete
    suspend fun delete(artist: RoomArtist)

    @Query("SELECT * FROM artist WHERE name = :artist")
    suspend fun getByName(artist: String): RoomArtist

    @Query("SELECT * FROM artist WHERE artistId = :artist")
    suspend fun getById(artist: String): RoomArtist
}