package com.example.androidmusicplayer.model.album

import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import com.example.androidmusicplayer.model.interfaces.SpotifyModel

data class Album(
    var albumId: String,
    var name: String,
    var imageString: String,
    var uriString: String,
): RoomModel<Album>, SpotifyModel<Album>, Model<Album> {
    lateinit var adapter: AlbumAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as AlbumAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Album>): Album {
        return adapter.fromSpotify(model as SpotifyAlbum)
    }
    override fun toRoom(model: Model<Album>): RoomModel<Album>? {
        TODO()
    }
    override fun fromRoom(model: RoomModel<Album>): Album? {
        return adapter.fromRoom(model as RoomAlbum)
    }
}