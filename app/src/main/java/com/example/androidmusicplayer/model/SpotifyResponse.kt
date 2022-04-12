package com.example.androidmusicplayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SpotifyResponse<T>(
    @Json(name = "items") var items: T,
    @Json(name = "next") var next: String,
)