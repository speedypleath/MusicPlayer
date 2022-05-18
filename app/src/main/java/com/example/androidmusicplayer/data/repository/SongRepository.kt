package com.example.androidmusicplayer.data.repository

import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.media.MediaItemTree
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SongRepository(
    private val mediaStoreDataSource: MediaStoreDataSource,
    private val spotifyDataSource: SpotifyDataSource,
    private val database: AppDatabase
) {
    private val mutex = Mutex()
    var songs: List<Song> = emptyList<Song>().toMutableList()

    suspend fun fetchSongs(refresh: Boolean = false): List<Song> {
        if (refresh || songs.isEmpty()) {
            val roomSongs = database.songDao().getAll()
            val aux = roomSongs.map { roomSong ->
                val roomArtist = database.artistDao().getById(roomSong.artist)
                val artist = Artist(
                    roomArtist.artistId,
                    roomArtist.name,
                    null,
                    roomArtist.uriString
                )
                val roomAlbum = database.albumDao().getById(roomSong.album)
                val album = Album(
                    roomAlbum.albumId,
                    roomAlbum.name,
                    null,
                    roomAlbum.uriString
                )
                Song(
                    roomSong.songId,
                    roomSong.title,
                    artist,
                    album,
                    0,
                    roomSong.imageString,
                    null,
                    roomSong.uriString
                )
            }

            aux.forEach { song ->
                MediaItemTree.addNodeToTree(song)
            }

            mutex.withLock {
                this.songs = aux
            }
        }

        return mutex.withLock { this.songs }
    }

    suspend fun fetchMediaStore() {
        val songResult = mediaStoreDataSource.fetchSongs()
        songResult.forEach { song ->
            song.artist?.toRoom()
            song.album?.toRoom()
            song.toRoom()
        }
    }

    suspend fun fetchSpotify() {
        val songResult = spotifyDataSource.fetchSongs()
        songResult.forEach { song ->
            song.artist?.toRoom()
            song.album?.toRoom()
            song.toRoom()
        }
    }

    fun getSong(name: String): Song? {
        return songs.find { song ->
            song.title == name
        }
    }
}
