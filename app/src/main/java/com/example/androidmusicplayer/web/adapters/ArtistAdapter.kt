package com.example.androidmusicplayer.web.adapters

import com.example.androidmusicplayer.model.artist.RoomArtist
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.example.androidmusicplayer.web.annotations.Artist
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.artist.Artist as ArtistModel

class ArtistAdapter {
    @ToJson
    fun toJson(@Artist json: List<SpotifyArtist>): SpotifyArtist {
        return json[0]
    }

    @Artist @FromJson
    fun fromJson(spotifyArtist: SpotifyArtist) = spotifyArtist

    fun fromRoom(roomArtist: RoomArtist): ArtistModel {
        return ArtistModel(
            roomArtist.artistId,
            roomArtist.name,
            roomArtist.imageString,
            roomArtist.uriString
        )
    }

    fun fromSpotify(spotifyArtist: SpotifyArtist): ArtistModel {
        return ArtistModel(
            spotifyArtist.artistId!!,
            spotifyArtist.name!!,
            spotifyArtist.images[0].url,
            spotifyArtist.uriString!!
        )
    }
}