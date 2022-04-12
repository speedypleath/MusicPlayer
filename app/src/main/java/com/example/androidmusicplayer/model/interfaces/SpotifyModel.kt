package com.example.androidmusicplayer.model.interfaces

interface SpotifyModel<T> {
    fun fromSpotify(): Model<T>?
}