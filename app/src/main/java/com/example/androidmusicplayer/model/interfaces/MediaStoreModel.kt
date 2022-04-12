package com.example.androidmusicplayer.model.interfaces

interface MediaStoreModel<T> {
    fun fromMediaStore(): Model<T>?
}