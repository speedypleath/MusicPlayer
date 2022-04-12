package com.example.androidmusicplayer.model.song

import android.net.Uri
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.model.album.MediaStoreAlbum
import com.example.androidmusicplayer.model.artist.MediaStoreArtist
import com.example.androidmusicplayer.model.interfaces.MediaStoreModel
import com.example.androidmusicplayer.model.interfaces.Model
import org.koin.core.component.inject

data class MediaStoreSong(
    val songId: Long,
    val name: String,
    val artist: MediaStoreArtist,
    val album: MediaStoreAlbum,
    val duration: Long,
    val uri: Uri
): Model<Song>, MediaStoreModel<Song> {
    private val adapter: SongAdapter by inject()

    override fun fromMediaStore(): Song {
        return adapter.fromMediaStore(this)
    }
}