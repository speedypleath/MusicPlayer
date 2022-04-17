package com.example.androidmusicplayer.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Size
import com.example.androidmusicplayer.AndroidMusicPlayer
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.data.dao.ArtistDao
import com.example.androidmusicplayer.data.dao.SongDao
import com.example.androidmusicplayer.model.SpotifyResponse
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.song.MediaStoreSong
import com.example.androidmusicplayer.model.song.RoomSong
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.model.song.SpotifySong
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class SongAdapter(
    private val artistDao: ArtistDao,
    private val albumDao: AlbumDao,
    private val songDao: SongDao
): Adapter {
    private val context = AndroidMusicPlayer.instance.applicationContext
    @ToJson
    fun toJson(spotifySongs: List<SpotifySong>) = spotifySongs

    @FromJson
    fun fromJson(json: SpotifyResponse<List<SpotifySong>>): List<SpotifySong> {
        return json.items
    }

    fun fromRoom(roomSong: RoomSong?): Song? {
        if(roomSong == null)
            return null

        val artist = artistDao.getById(roomSong.artist)?.fromRoom()
        val album = albumDao.getById(roomSong.album)?.fromRoom() as Album
        return Song(
            roomSong.songId,
            roomSong.title,
            artist,
            album,
            roomSong.length,
            roomSong.uriString,
            null,
            roomSong.uriString
        )
    }

    fun fromMediaStore(mediaStoreSong: MediaStoreSong): Song {
        val artist = mediaStoreSong.artist.fromMediaStore() as Artist
        val album = mediaStoreSong.album.fromMediaStore()
        val image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                context.contentResolver.loadThumbnail(Uri.parse("content://media/external/audio/" + mediaStoreSong.songId.toString()), Size(128, 128), null)
            } catch (e: Exception) {
                BitmapFactory.decodeStream(context.assets.open("placeholder-images-image_large.webp"))
            }
        } else {
            BitmapFactory.decodeStream(context.assets.open("placeholder-images-image_large.webp"))
        }
        return Song(
            mediaStoreSong.songId.toString(),
            mediaStoreSong.name,
            artist,
            album,
            mediaStoreSong.duration,
            "content://media/external/audio/" + mediaStoreSong.songId.toString(),
            image,
            mediaStoreSong.uri.encodedPath!!
        )
    }

    fun fromSpotify(spotifySong: SpotifySong?): Song? {
        if(spotifySong == null)
            return null
        val artist = spotifySong.artist[0].fromSpotify()
        val album = spotifySong.album.fromSpotify()
        return spotifySong.album.images[0].url?.let {
            Song(
                spotifySong.songId,
                spotifySong.title,
                artist,
                album,
                spotifySong.length,
                it,
                null,
                spotifySong.uri
            )
        }
    }

    fun spotifyToRoom(spotifySong: SpotifySong) {
        val roomSong = RoomSong(
            spotifySong.songId,
            spotifySong.title,
            spotifySong.artist[0].artistId!!,
            spotifySong.album.albumId,
            spotifySong.length,
            spotifySong.album.images[0].url,
            spotifySong.uri
        )
        songDao.insertAll(roomSong)
    }

    fun mediaStoreToRoom(mediaStoreSong: MediaStoreSong) {
        val roomSong = RoomSong(
            mediaStoreSong.songId.toString(),
            mediaStoreSong.name,
            mediaStoreSong.artist.artistId.toString(),
            mediaStoreSong.album.albumId.toString(),
            mediaStoreSong.duration,
            "content://media/external/audio/albumart/" + mediaStoreSong.songId.toString(),
            mediaStoreSong.uri.encodedPath!!
        )
        songDao.insertAll(roomSong)
    }
}