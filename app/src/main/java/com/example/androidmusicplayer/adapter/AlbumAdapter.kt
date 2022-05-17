package com.example.androidmusicplayer.adapter

import com.example.androidmusicplayer.data.api.ImageApi
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.model.album.MediaStoreAlbum
import com.example.androidmusicplayer.model.album.RoomAlbum
import com.example.androidmusicplayer.model.album.SpotifyAlbum
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

    suspend fun toRoom(album: AlbumModel) {

        val roomAlbum = RoomAlbum(
            album.albumId,
            album.name,
            album.image.toString(),
            album.uriString
        )
        albumDao.insert(roomAlbum)
    }
}