package com.example.androidmusicplayer.model.song

import com.example.androidmusicplayer.model.album.SpotifyAlbum
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotifySong(
    @Json(name = "id") var songId: String,
    @Json(name = "name") var title: String,
    @Json(name = "artists") var artist: List<SpotifyArtist>,
    @Json(name = "duration_ms") var length: Long,
    @Json(name = "album") var album: SpotifyAlbum,
    @Json(name = "uri") var uri: String
)