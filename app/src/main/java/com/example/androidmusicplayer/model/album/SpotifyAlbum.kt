package com.example.androidmusicplayer.model.album

import com.example.androidmusicplayer.adapter.AlbumAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.koin.core.component.inject

@JsonClass(generateAdapter = true)
data class SpotifyAlbum(
    @Json(name = "id") var albumId: String,
    @Json(name = "name") var name: String,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String,
) : SpotifyModel<Album>, Model<Album> {
    val type = "spotify"
    @delegate:Transient
    private val adapter: AlbumAdapter by inject()

    override fun fromSpotify(): Album {
        return adapter.fromSpotify(this)
    }
}