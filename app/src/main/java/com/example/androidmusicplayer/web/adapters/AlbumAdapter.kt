package com.example.androidmusicplayer.web.adapters

import com.example.androidmusicplayer.web.annotations.Album
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.Album as AlbumModel

class AlbumAdapter {
    @ToJson
    fun toJson(@Album album: String?) = album

    @FromJson
    @Album
    fun fromJson(json: AlbumModel): String {
        return json.name
    }
}