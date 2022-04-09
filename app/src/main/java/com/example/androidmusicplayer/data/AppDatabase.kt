package com.example.androidmusicplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.data.dao.ArtistDao
import com.example.androidmusicplayer.data.dao.PlaylistDao
import com.example.androidmusicplayer.data.dao.SongDao
import com.example.androidmusicplayer.model.album.RoomAlbum
import com.example.androidmusicplayer.model.artist.RoomArtist
import com.example.androidmusicplayer.model.genre.Genre
import com.example.androidmusicplayer.model.playlist.RoomPlaylist
import com.example.androidmusicplayer.model.relations.GenreSongCrossRef
import com.example.androidmusicplayer.model.relations.PlaylistSongCrossRef
import com.example.androidmusicplayer.model.song.RoomSong
import com.example.androidmusicplayer.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [RoomSong::class, RoomPlaylist::class, PlaylistSongCrossRef::class,
                     RoomArtist::class, RoomAlbum::class, GenreSongCrossRef::class,
                     Genre::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun artistDao(): ArtistDao
    abstract fun albumDao(): AlbumDao
}
