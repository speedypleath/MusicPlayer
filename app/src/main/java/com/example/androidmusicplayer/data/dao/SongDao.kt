package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.playlist.PlaylistWithSongs
import com.example.androidmusicplayer.model.song.RoomSong

@Dao
interface SongDao {
    @Insert
    fun insertAll(vararg songs: RoomSong)

    @Update
    fun updateAll(vararg songs: RoomSong)

    @Delete
    fun deleteAll(vararg songs: RoomSong)

    @Query("SELECT * FROM song WHERE uri = :uri")
    fun getByUri(uri: String): RoomSong?

    @Query("SELECT * FROM song WHERE title = :song")
    fun getByName(song: String): List<RoomSong>?

    @Query("SELECT * FROM song WHERE album = :album")
    fun getByAlbum(album: String): List<RoomSong>?

    @Query("SELECT * FROM song WHERE artist = :artist")
    fun getByArtist(artist: String): List<RoomSong>?

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getPlaylistsWithSongs(): List<PlaylistWithSongs>?
}