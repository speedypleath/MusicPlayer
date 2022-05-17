package com.example.androidmusicplayer.adapter

import com.example.androidmusicplayer.data.api.ImageApi
import com.example.androidmusicplayer.data.dao.ArtistDao
import com.example.androidmusicplayer.model.artist.MediaStoreArtist
import com.example.androidmusicplayer.model.artist.RoomArtist
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.web.annotations.Artist
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.artist.Artist as ArtistModel

class ArtistAdapter(
    private val imageApi: ImageApi,
    private val artistDao: ArtistDao
): Adapter {
    @ToJson
    fun toJson(@Artist json: List<SpotifyArtist>?): SpotifyArtist? {
        return json?.get(0)
    }

    @FromJson
    fun fromJson(spotifyArtist: SpotifyArtist?) = spotifyArtist

    fun fromRoom(roomArtist: RoomArtist): ArtistModel {
        val image = roomArtist.uriString.let { imageApi.getBitmapFromUrl(it) }

        return ArtistModel(
            roomArtist.artistId,
            roomArtist.name,
            image,
            roomArtist.uriString
        )
    }

    fun fromSpotify(spotifyArtist: SpotifyArtist): ArtistModel {
        val image = spotifyArtist.uriString?.let { imageApi.getBitmapFromUrl(it) }
        return ArtistModel(
            spotifyArtist.artistId!!,
            spotifyArtist.name!!,
            image,
            spotifyArtist.uriString!!
        )
    }

    fun fromMediaStore(mediaStoreArtist: MediaStoreArtist): ArtistModel {
        val image = imageApi.getBitmapFromUrl("content://media/external/audio/albumart/" + mediaStoreArtist.artistId.toString())
        return ArtistModel(
            mediaStoreArtist.artistId.toString(),
            mediaStoreArtist.name,
            image,
            mediaStoreArtist.uri.encodedPath
        )
    }

    fun spotifyToRoom(spotifyArtist: SpotifyArtist) {
        val image = if(spotifyArtist.images != null)
            spotifyArtist.images!![0].url
        else
            null

        val roomArtist = RoomArtist(
            spotifyArtist.artistId!!,
            spotifyArtist.name!!,
            image!!,
            spotifyArtist.uriString!!
        )
        artistDao.insert(roomArtist)
    }

    fun mediaStoreToRoom(mediaStoreArtist: MediaStoreArtist) {
        val roomArtist = RoomArtist(
            mediaStoreArtist.artistId.toString(),
            mediaStoreArtist.name,
            "content://media/external/audio/albumart/" + mediaStoreArtist.artistId.toString(),
            mediaStoreArtist.uri.encodedPath!!
        )
        artistDao.insert(roomArtist)
    }
}