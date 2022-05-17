package com.example.androidmusicplayer.model.playlist

import com.example.androidmusicplayer.adapter.PlaylistAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.koin.core.component.inject


@JsonClass(generateAdapter = true)
data class SpotifyPlaylist(
    @Json(name = "id") var playlistId: String,
    @Json(name = "name") var name: String,
    @Json(name = "description") var description: String,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String,
): SpotifyModel<Playlist>, Model<Playlist> {
    val type = "spotify"
    @delegate:Transient
    private val adapter: PlaylistAdapter by inject()

    override fun fromSpotify(): Playlist {
        TODO()
    }
}