package com.example.androidmusicplayer.model.album

import android.net.Uri
import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.model.interfaces.MediaStoreModel
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

class MediaStoreAlbum(
    var albumId: Long,
    var name: String,
    var uri: Uri,
): Model<Album>, MediaStoreModel<Album> {
    private val adapter: AlbumAdapter by inject()

    override fun fromMediaStore(): Album {
        return adapter.fromMediaStore(this)
    }
}