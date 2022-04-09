package com.example.androidmusicplayer.model.interfaces

interface MediaStoreModel<T> {
    fun toMediaStore(model: Model<T>): MediaStoreModel<T>?
    fun fromMediaStore(model: MediaStoreModel<T>): Model<T>?
}