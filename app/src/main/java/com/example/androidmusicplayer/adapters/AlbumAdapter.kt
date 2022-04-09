package com.example.androidmusicplayer.adapters

import com.example.androidmusicplayer.model.album.RoomAlbum
import com.example.androidmusicplayer.model.album.SpotifyAlbum
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.web.annotations.Album
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.album.Album as AlbumModel

class AlbumAdapter: Adapter {
    @ToJson
    fun toJson(@Album album: String?) = album

    @FromJson
    @Album
    fun fromJson(json: AlbumModel): String {
        return json.name
    }

    fun fromRoom(roomAlbum: RoomAlbum?): AlbumModel? {
        return if(roomAlbum != null)
            AlbumModel(
            roomAlbum.albumId,
            roomAlbum.name,
            roomAlbum.imageString,
            roomAlbum.uriString) else null
    }

    fun fromSpotify(spotifyAlbum: SpotifyAlbum): AlbumModel {
        return AlbumModel(
            spotifyAlbum.albumId,
            spotifyAlbum.name,
            spotifyAlbum.images[0].url,
            spotifyAlbum.uriString
        )
    }
}