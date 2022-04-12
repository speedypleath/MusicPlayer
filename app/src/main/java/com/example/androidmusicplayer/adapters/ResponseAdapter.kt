package com.example.androidmusicplayer.adapters

import com.example.androidmusicplayer.model.SpotifyResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ResponseAdapter {
    @ToJson
    fun toJson(json: Any) = json

    @FromJson
    fun fromJson(json: SpotifyResponse<*>): Any? {
        return json.items
    }
}