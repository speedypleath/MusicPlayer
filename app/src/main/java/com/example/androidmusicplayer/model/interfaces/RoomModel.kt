package com.example.androidmusicplayer.model.interfaces

interface RoomModel<T> {
    fun fromRoom(): Model<T>?
}