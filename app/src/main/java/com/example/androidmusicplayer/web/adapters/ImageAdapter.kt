package com.example.androidmusicplayer.web.adapters

import com.example.androidmusicplayer.model.SerializedImage
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ImageAdapter {
    @ToJson
    fun toJson(@Image image: String?) = image

    @FromJson
    @Image
    fun fromJson(json: Array<SerializedImage>): String {
        return json[0].url
    }
}