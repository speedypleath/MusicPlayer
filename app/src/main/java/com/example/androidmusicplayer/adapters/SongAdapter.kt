package com.example.androidmusicplayer.adapters

import com.example.androidmusicplayer.ImageApi
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.data.dao.ArtistDao
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.song.MediaStoreSong
import com.example.androidmusicplayer.model.song.RoomSong
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.model.song.SpotifySong

class SongAdapter(
    private val imageApi: ImageApi,
    private val artistDao: ArtistDao,
    private val albumDao: AlbumDao,
    private val albumAdapter: AlbumAdapter,
    private val artistAdapter: ArtistAdapter
): Adapter {
    fun roomToSong(roomSong: RoomSong?): Song? {
        if(roomSong == null)
            return null

        val image = imageApi.getBitmapFromUrl(roomSong.uriString)
        val artist = artistAdapter.fromRoom(artistDao.getById(roomSong.artist))
        val album = albumAdapter.fromRoom(albumDao.getById(roomSong.album))
        return Song(
            roomSong.songId,
            roomSong.title,
            artist,
            album,
            roomSong.length,
            image,
            roomSong.uriString
        )
    }

    fun mediaStoreToSong(mediaStoreSong: MediaStoreSong?): Song? {
        if(mediaStoreSong == null || artistDao.getById(mediaStoreSong.artist) == null || albumDao.getById(mediaStoreSong.album) == null)
            return null

        val image = imageApi.getBitmapFromUrl("content://media/external/audio/albumart/" + mediaStoreSong.songId.toString())

        val artist = artistAdapter.fromRoom(artistDao.getById(mediaStoreSong.artist))
        val album = albumAdapter.fromRoom(albumDao.getById(mediaStoreSong.album))
        return Song(
            mediaStoreSong.songId.toString(),
            mediaStoreSong.name,
            artist,
            album,
            mediaStoreSong.duration,
            image,
            mediaStoreSong.uri.encodedPath!!
        )
    }

    fun fromSpotify(spotifySong: SpotifySong?): Song? {
        if(spotifySong == null)
            return null
        val image = imageApi.getBitmapFromUrl(spotifySong.uri)
        val artist = artistAdapter.fromSpotify(spotifySong.artist[0])
        val album = albumAdapter.fromSpotify(spotifySong.album)
        return Song(
            spotifySong.songId,
            spotifySong.title,
            artist,
            album,
            spotifySong.length, image,
            spotifySong.uri
        )
    }

    fun spotifyToRoom(spotifySong: SpotifySong): RoomSong {
        return RoomSong(
            spotifySong.songId,
            spotifySong.title,
            spotifySong.artist[0].artistId!!,
            spotifySong.album.albumId,
            spotifySong.length,
            spotifySong.album.images[0].url,
            spotifySong.uri
        )
    }

    fun mediaStoreToRoom(mediaStoreSong: MediaStoreSong): RoomSong {
        return RoomSong(
            mediaStoreSong.songId.toString(),
            mediaStoreSong.name,
            mediaStoreSong.artist,
            mediaStoreSong.album,
            mediaStoreSong.duration,
            "content://media/external/audio/albumart/" + mediaStoreSong.songId.toString(),
            mediaStoreSong.uri.encodedPath!!
        )
    }
}