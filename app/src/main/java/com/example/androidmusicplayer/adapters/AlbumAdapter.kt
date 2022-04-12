package com.example.androidmusicplayer.adapters

import com.example.androidmusicplayer.ImageApi
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.model.album.MediaStoreAlbum
import com.example.androidmusicplayer.model.album.RoomAlbum
import com.example.androidmusicplayer.model.album.SpotifyAlbum
import com.example.androidmusicplayer.model.artist.SpotifyArtist
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.web.annotations.Album
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.album.Album as AlbumModel

class AlbumAdapter(
    private val imageApi: ImageApi,
    private val albumDao: AlbumDao
): Adapter {
    @ToJson
    fun toJson(@Album album: String?) = album

    @FromJson
    @Album
    fun fromJson(json: AlbumModel): String {
        return json.name
    }

    fun fromRoom(roomAlbum: RoomAlbum): AlbumModel {
        val image = roomAlbum.uriString.let { imageApi.getBitmapFromUrl(it) }
        return AlbumModel(
            roomAlbum.albumId,
            roomAlbum.name,
            image,
            roomAlbum.uriString)
    }

    fun fromSpotify(spotifyAlbum: SpotifyAlbum): AlbumModel {
        val image = spotifyAlbum.uriString.let { imageApi.getBitmapFromUrl(it) }
        return AlbumModel(
            spotifyAlbum.albumId,
            spotifyAlbum.name,
            image,
            spotifyAlbum.uriString
        )
    }

    fun fromMediaStore(mediaStoreAlbum: MediaStoreAlbum): AlbumModel {
        val image = imageApi.getBitmapFromUrl("content://media/external/audio/albumart/" + mediaStoreAlbum.albumId.toString())
        return AlbumModel(
            mediaStoreAlbum.albumId.toString(),
            mediaStoreAlbum.name,
            image,
            mediaStoreAlbum.uri.encodedPath!!
        )
    }

    fun spotifyToRoom(spotifyAlbum: SpotifyArtist) {
        val image = if(spotifyAlbum.images != null)
            spotifyAlbum.images!![0].url
        else
            null

        val roomAlbum = RoomAlbum(
            spotifyAlbum.artistId!!,
            spotifyAlbum.name!!,
            image!!,
            spotifyAlbum.uriString!!
        )
        albumDao.insert(roomAlbum)
    }

    fun mediaStoreToRoom(mediaStoreAlbum: MediaStoreAlbum) {
        val roomAlbum = RoomAlbum(
            mediaStoreAlbum.albumId.toString(),
            mediaStoreAlbum.name,
            "content://media/external/audio/albumart/" + mediaStoreAlbum.albumId.toString(),
            mediaStoreAlbum.uri.encodedPath!!
        )
        albumDao.insert(roomAlbum)
    }
}