package com.example.androidmusicplayer.model.album

import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotifyAlbum(
    @Json(name = "id") var albumId: String,
    @Json(name = "name") var name: String,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String,
) : SpotifyModel<Album>, Model<Album> {
    val type = "spotify"
    lateinit var adapter: AlbumAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as AlbumAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Album>): Album {
        return adapter.fromSpotify(model as SpotifyAlbum)
    }
}