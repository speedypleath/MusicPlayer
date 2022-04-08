package com.example.androidmusicplayer.model.artist

import androidx.room.Entity
import com.example.androidmusicplayer.model.SpotifyImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class SpotifyArtist(
    @Json(name = "id") var artistId: String?,
    @Json(name = "name") var name: String?,
    @Json(name = "genres") var genres: List<String>?,
    @Json(name = "images") var images: List<SpotifyImage>,
    @Json(name = "uri") var uriString: String?,
)