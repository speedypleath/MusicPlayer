package com.example.androidmusicplayer.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Size
import com.example.androidmusicplayer.AndroidMusicPlayer
import com.example.androidmusicplayer.data.dao.SongDao
import com.example.androidmusicplayer.model.SpotifyResponse
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.song.MediaStoreSong
import com.example.androidmusicplayer.model.song.RoomSong
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.model.song.SpotifySong
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class SongAdapter(
    private val songDao: SongDao
): Adapter {
    private val context = AndroidMusicPlayer.instance.applicationContext
    @ToJson
    fun toJson(spotifySongs: List<SpotifySong>) = spotifySongs

    @FromJson
    fun fromJson(json: SpotifyResponse<List<SpotifySong>>): List<SpotifySong> {
        return json.items
    }

    fun fromMediaStore(mediaStoreSong: MediaStoreSong): Song {
        val artist = mediaStoreSong.artist.fromMediaStore() as Artist
        val album = mediaStoreSong.album.fromMediaStore()
        val image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                context.contentResolver.loadThumbnail(Uri.parse("content://media/external/audio/albumart/" + mediaStoreSong.songId), Size(128, 128), null)
            } catch (e: Exception) {
//                Log.d("Song Adapter", "Exception: ${e.stackTraceToString()}")
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
            "content://media/external/audio/albumart/" + mediaStoreSong.songId.toString(),
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

    suspend fun toRoom(song: Song) {
        val roomSong = song.uri?.let {
            song.album?.let { it1 ->
                song.artist?.let { it2 ->
                    RoomSong(
                        song.songId,
                        song.title,
                        it2.artistId,
                        it1.albumId,
                        0,
                        "content://media/external/audio/albumart/" + song.songId,
                        it
                    )
                }
            }
        }
        if (roomSong != null) {
            songDao.insertAll(roomSong)
        }
    }
}