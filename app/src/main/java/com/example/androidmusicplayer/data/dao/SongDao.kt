package com.example.androidmusicplayer.data.dao

import androidx.room.*
import com.example.androidmusicplayer.model.playlist.PlaylistWithSongs
import com.example.androidmusicplayer.model.song.RoomSong
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg songs: RoomSong)

    @Update
    suspend fun updateAll(vararg songs: RoomSong)

    @Delete
    suspend fun deleteAll(vararg songs: RoomSong)

    @Query("SELECT * FROM song")
    suspend fun getAll(): List<RoomSong>

    @Query("SELECT * FROM song WHERE uri = :uri")
    suspend fun getByUri(uri: String): RoomSong

    @Query("SELECT * FROM song WHERE title = :song")
    suspend fun getByName(song: String): RoomSong

    @Query("SELECT * FROM song WHERE album = :album")
    suspend fun getByAlbum(album: String): List<RoomSong>

    @Query("SELECT * FROM song WHERE artist = :artist")
    suspend fun getByArtist(artist: String): List<RoomSong>

    @Transaction
    @Query("SELECT * FROM playlist")
    suspend fun getPlaylistsWithSongs(): List<PlaylistWithSongs>
}