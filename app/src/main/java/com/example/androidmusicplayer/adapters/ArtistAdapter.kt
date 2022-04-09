package com.example.androidmusicplayer.adapters

import com.example.androidmusicplayer.model.SpotifyImage
import com.example.androidmusicplayer.model.artist.RoomArtist
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.web.annotations.Artist
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.artist.Artist as ArtistModel

class ArtistAdapter: Adapter {
    @ToJson
    fun toJson(@Artist json: List<SpotifyArtist>?): SpotifyArtist? {
        return json?.get(0)
    }

    @FromJson
    fun fromJson(spotifyArtist: SpotifyArtist?) = spotifyArtist

    fun fromRoom(roomArtist: RoomArtist?): ArtistModel? {
        if(roomArtist == null)
            return null

        return ArtistModel(
            roomArtist.artistId,
            roomArtist.name,
            roomArtist.imageString,
            roomArtist.uriString
        )
    }

    fun toSpotify(artist: ArtistModel?): SpotifyArtist? {
        if (artist == null)
            return null

        return SpotifyArtist(
            artist.artistId,
            artist.name,
            emptyList(),
            listOf(SpotifyImage(artist.imageString)),
            artist.uriString
        )
    }

    fun fromSpotify(spotifyArtist: SpotifyArtist?): ArtistModel? {
        if (spotifyArtist == null)
            return null

        return ArtistModel(
            spotifyArtist.artistId!!,
            spotifyArtist.name!!,
            spotifyArtist.images!![0].url,
            spotifyArtist.uriString!!
        )
    }
}