package com.example.androidmusicplayer.adapter

import android.net.Uri
import com.example.androidmusicplayer.data.api.ImageApi
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.playlist.Playlist
import com.example.androidmusicplayer.model.playlist.RoomPlaylist

class PlaylistAdapter(
    private val imageApi: ImageApi
): Adapter {

    fun roomToPlaylist(roomPlaylist: RoomPlaylist?): Playlist? {
        if(roomPlaylist == null)
            return null

        val image = roomPlaylist.imageString?.let { imageApi.getBitmapFromUrl(it) }
        return Playlist(
            roomPlaylist.playlistId,
            roomPlaylist.name,
            roomPlaylist.description,
            image,
            Uri.parse(roomPlaylist.uriString)
        )
    }
}