package com.example.androidmusicplayer.model.artist

import androidx.room.Entity
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class SpotifyArtist(
    @Json(name = "id") var artistId: String?,
    @Json(name = "name") var name: String?,
    @Json(name = "genres") var genres: List<String>?,
    @Json(name = "images") var images: List<SpotifyImage>?,
    @Json(name = "uri") var uriString: String?,
) : SpotifyModel<Artist>, Model<Artist> {
    val type = "spotify"

    lateinit var adapter: ArtistAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as ArtistAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Artist>): Artist? {
       return adapter.fromSpotify(model as SpotifyArtist)
    }
}