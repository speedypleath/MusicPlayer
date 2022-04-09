package com.example.androidmusicplayer.model.song

import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.model.album.SpotifyAlbum
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
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
): SpotifyModel<Song>, Model<Song>{
    lateinit var adapter: SongAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as SongAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Song>): Song? {
        return adapter.fromSpotify(model as SpotifySong)
    }
}
