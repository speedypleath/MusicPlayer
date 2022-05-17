package com.example.androidmusicplayer.model.artist

import androidx.room.Entity
import com.example.androidmusicplayer.adapter.ArtistAdapter
import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.SpotifyModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.koin.core.component.inject

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
    @delegate:Transient
    private val adapter: ArtistAdapter by inject()

    override fun fromSpotify(): Artist {
       return adapter.fromSpotify(this)
    }
}