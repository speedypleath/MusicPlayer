package com.example.androidmusicplayer.model.song

import android.graphics.Bitmap
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.interfaces.*

data class Song(
    var songId: String,
    var title: String,
    var artist: Artist?,
    var album: Album?,
    var length: Long,
    var image: Bitmap,
    var uri: String,
    var genres: List<String> = listOf(),
): RoomModel<Song>, Model<Song>, SpotifyModel<Song>, MediaStoreModel<Song> {
    companion object {
        const val TABLE_NAME = "song"
    }
    val type = "room"
    lateinit var adapter: SongAdapter

    fun bind(adapter: Adapter) {
        this.adapter = adapter as SongAdapter
    }

    override fun toRoom(model: Model<Song>): RoomModel<Song>? {
        TODO()
    }

    override fun toMediaStore(model: Model<Song>): MediaStoreModel<Song>? = null

    override fun fromMediaStore(model: MediaStoreModel<Song>): Model<Song>? {
        return adapter.mediaStoreToSong(model as MediaStoreSong)
    }

    override fun fromRoom(model: RoomModel<Song>): Model<Song>? {
        return adapter.roomToSong(model as RoomSong)
    }

    override fun fromSpotify(model: SpotifyModel<Song>): Model<Song>? {
        return adapter.fromSpotify(model as SpotifySong)
    }
}