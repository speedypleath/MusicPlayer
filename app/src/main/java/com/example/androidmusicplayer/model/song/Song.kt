package com.example.androidmusicplayer.model.song

import android.graphics.Bitmap
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist

data class Song(
    var songId: String,
    var title: String,
    var artist: Artist,
    var album: Album,
    var length: Long,
    var image: Bitmap,
    var uri: String,
    var genres: List<String> = listOf(),
)