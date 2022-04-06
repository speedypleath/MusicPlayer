package com.example.androidmusicplayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SerializedImage(
    @Json(name = "height")
    var height: Int?,
    @Json(name = "url")
    var url: String,
    @Json(name = "width")
    var width: Int?
)