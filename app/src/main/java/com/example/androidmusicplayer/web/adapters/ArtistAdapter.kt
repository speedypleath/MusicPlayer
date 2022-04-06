package com.example.androidmusicplayer.web.adapters

import com.example.androidmusicplayer.web.annotations.Artist
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.example.androidmusicplayer.model.Artist as ArtistModel

class ArtistAdapter {
    @ToJson
    fun toJson(@Artist artist: String?) = artist

    @FromJson
    @Artist
    fun fromJson(json: Array<ArtistModel>): String {
        return json[0].name
    }
}