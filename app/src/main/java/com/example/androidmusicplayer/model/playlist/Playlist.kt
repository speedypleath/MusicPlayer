package com.example.androidmusicplayer.model.playlist

import android.graphics.Bitmap
import android.net.Uri
import com.example.androidmusicplayer.adapters.PlaylistAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

data class Playlist(
    var playlistId: String,
    var description: String,
    var name: String,
    var imageString: Bitmap?,
    var uri: Uri?,
): Model<Playlist> {
    private val adapter: PlaylistAdapter by inject()
}