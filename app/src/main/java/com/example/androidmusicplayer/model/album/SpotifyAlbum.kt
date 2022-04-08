package com.example.androidmusicplayer.model.album

import com.example.androidmusicplayer.model.SpotifyImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotifyAlbum(
    @Json(name = "id") var albumId: String,
    @Json(name = "name") var name: String,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String,
)