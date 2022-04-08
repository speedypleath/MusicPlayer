package com.example.androidmusicplayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SpotifyImage(
    @Json(name = "url")
    var url: String,
)