package com.example.androidmusicplayer.model.playlist

import android.graphics.Bitmap
import android.net.Uri
import com.example.androidmusicplayer.adapters.PlaylistAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import com.example.androidmusicplayer.model.interfaces.SpotifyModel

data class Playlist(
    var playlistId: String,
    var description: String,
    var name: String,
    var imageString: Bitmap,
    var uri: Uri?,
): RoomModel<Playlist>, SpotifyModel<Playlist>, Model<Playlist> {
    lateinit var adapter: PlaylistAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as PlaylistAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Playlist>): Playlist? {
        TODO()
    }

    override fun toRoom(model: Model<Playlist>): RoomModel<Playlist>? {
        TODO()
    }

    override fun fromRoom(model: RoomModel<Playlist>): Playlist? {
        TODO()
    }
}