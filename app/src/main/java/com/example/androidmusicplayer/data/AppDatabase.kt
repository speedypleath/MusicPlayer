package com.example.androidmusicplayer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
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

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "song_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
