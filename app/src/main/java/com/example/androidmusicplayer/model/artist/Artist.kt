package com.example.androidmusicplayer.model.artist

import android.graphics.Bitmap
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

class Artist(
    var artistId: String,
    var name: String,
    var image: Bitmap?,
    var uriString: String?,
): Model<Artist> {
    private val adapter: ArtistAdapter by inject()
}