package com.example.androidmusicplayer.data.song

import androidx.room.*
import com.example.androidmusicplayer.model.PlaylistWithSongs
import com.example.androidmusicplayer.model.Song

@Dao
interface SongDao {
    @Insert
    fun insertAll(vararg songs: Song)

    @Update
    fun updateAll(vararg songs: Song)

    @Delete
    fun deleteAll(vararg songs: Song)

    @Query("SELECT * FROM song WHERE uri = :uri")
    fun getByUri(uri: String): Song

    @Query("SELECT * FROM song WHERE title = :song")
    fun getByName(song: String): List<Song>

    @Query("SELECT * FROM song WHERE album = :album")
    fun getByAlbum(album: String): List<Song>

    @Query("SELECT * FROM song WHERE artist = :artist")
    fun getByArtist(artist: String): List<Song>

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getPlaylistsWithSongs(): List<PlaylistWithSongs>
}