package com.example.androidmusicplayer.model.playlist

import android.graphics.Bitmap
import android.net.Uri

data class Playlist(
    var playlistId: String,
    var description: String,
    var name: String,
    var imageString: Bitmap,
    var uri: Uri?,
)