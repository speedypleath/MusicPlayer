package com.example.androidmusicplayer.model.album

import android.graphics.Bitmap
import com.example.androidmusicplayer.adapter.AlbumAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

data class Album(
    var albumId: String,
    var name: String,
    var image: Bitmap?,
    var uriString: String,
): Model<Album> {
    private val adapter: AlbumAdapter by inject()
}