package com.example.androidmusicplayer.model.interfaces

interface RoomModel<T> {
    fun toRoom(model: Model<T>): RoomModel<T>?
    fun fromRoom(model: RoomModel<T>): Model<T>?
}