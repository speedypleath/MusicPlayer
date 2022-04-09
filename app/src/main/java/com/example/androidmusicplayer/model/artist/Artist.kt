package com.example.androidmusicplayer.model.artist

import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import com.example.androidmusicplayer.model.interfaces.SpotifyModel

class Artist(
    var artistId: String,
    var name: String,
    var imageString: String,
    var uriString: String,
): RoomModel<Artist>, SpotifyModel<Artist>, Model<Artist> {
    lateinit var adapter: ArtistAdapter
    fun bind(adapter: Adapter) {
        this.adapter = adapter as ArtistAdapter
    }

    override fun fromSpotify(model: SpotifyModel<Artist>): Artist? {
        return adapter.fromSpotify(model as SpotifyArtist)
    }

    override fun toRoom(model: Model<Artist>): RoomModel<Artist>? {
        TODO()
    }
    override fun fromRoom(model: RoomModel<Artist>): Artist? {
        return adapter.fromRoom(model as RoomArtist)
    }
}