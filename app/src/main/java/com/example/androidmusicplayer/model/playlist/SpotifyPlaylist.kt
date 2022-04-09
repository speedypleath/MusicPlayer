package com.example.androidmusicplayer.model.playlist

import com.example.androidmusicplayer.adapters.PlaylistAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SpotifyPlaylist(
    @Json(name = "id") var playlistId: String,
    @Json(name = "name") var name: String,
    @Json(name = "description") var description: String,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String,
): SpotifyModel<Playlist>, Model<Playlist> {
    val type = "spotify"
    lateinit var adapter: PlaylistAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as PlaylistAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Playlist>): Playlist {
        TODO()
    }
}