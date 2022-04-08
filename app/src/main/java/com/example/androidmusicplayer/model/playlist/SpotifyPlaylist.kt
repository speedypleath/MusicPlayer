package com.example.androidmusicplayer.model.playlist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SpotifyPlaylist(
    @Json(name = "id") var playlistId: String,
    @Json(name = "name") var name: String,
    @Json(name = "description") var description: String,
    @Json(name = "images") var imageString: String,
    @Json(name = "uri") var uriString: String,
)