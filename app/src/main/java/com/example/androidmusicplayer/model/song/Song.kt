package com.example.androidmusicplayer.model.song

import android.graphics.Bitmap
import com.example.androidmusicplayer.adapter.SongAdapter
import com.example.androidmusicplayer.model.album.Album
import com.example.androidmusicplayer.model.artist.Artist
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

data class Song(
    var songId: String,
    var title: String,
    var artist: Artist?,
    var album: Album?,
    var length: Long,
    var imageUri: String?,
    var image: Bitmap?,
    var uri: String?,
): Model<Song> {
    private val adapter: SongAdapter by inject()
}