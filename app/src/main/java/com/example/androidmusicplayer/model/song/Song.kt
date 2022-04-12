package com.example.androidmusicplayer.model.song

import com.example.androidmusicplayer.adapters.SongAdapter
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
    var imageUri: String,
    var uri: String,
    var genres: List<String> = listOf(),
): Model<Song> {
    private val adapter: SongAdapter by inject()
}