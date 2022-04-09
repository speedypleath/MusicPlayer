package com.example.androidmusicplayer.model.song

import android.net.Uri
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel

data class MediaStoreSong(
    val songId: Long,
    val name: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val uri: Uri
): RoomModel<Song>, Model<Song> {
    private lateinit var adapter: SongAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as SongAdapter
    }

    fun fromMediaStore(model: MediaStoreSong): Song? {
        return adapter.mediaStoreToSong(model)
    }

    override fun toRoom(model: Model<Song>): RoomModel<Song>? {
        TODO()
    }

    override fun fromRoom(model: RoomModel<Song>): Song? {
        return adapter.roomToSong(model as RoomSong)
    }
}