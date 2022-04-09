package com.example.androidmusicplayer.model.interfaces

interface SpotifyModel<T> {
    fun fromSpotify(model: SpotifyModel<T>): Model<T>?
}