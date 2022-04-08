package com.example.androidmusicplayer.model.song

import android.net.Uri

data class MediaStoreSong(
    val songId: Long,
    val name: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val uri: Uri
)