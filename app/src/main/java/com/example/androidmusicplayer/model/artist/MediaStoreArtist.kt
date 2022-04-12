package com.example.androidmusicplayer.model.artist

import android.net.Uri
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.model.interfaces.MediaStoreModel
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

class MediaStoreArtist(
    var artistId: Long,
    var name: String,
    var uri: Uri,
): Model<Artist>, MediaStoreModel<Artist> {
    private val adapter: ArtistAdapter by inject()
    override fun fromMediaStore(): Model<Artist> {
        return adapter.fromMediaStore(this)
    }

}