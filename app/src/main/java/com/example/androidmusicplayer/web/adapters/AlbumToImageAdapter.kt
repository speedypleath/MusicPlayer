package com.example.androidmusicplayer.web.adapters
import com.example.androidmusicplayer.web.annotations.Album
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.Album as AlbumModel

class AlbumToImageAdapter {
    @ToJson
    fun toJson(@Album @Image image: String?) = image

    @FromJson
    @Album @Image
    fun fromJson(json: AlbumModel): String? {
        return json.imageString
    }
}