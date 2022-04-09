package com.example.androidmusicplayer.adapters

import android.net.Uri
import com.example.androidmusicplayer.ImageApi
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.playlist.Playlist
import com.example.androidmusicplayer.model.playlist.RoomPlaylist
import com.example.androidmusicplayer.model.playlist.SpotifyPlaylist

class PlaylistAdapter(
    private val imageApi: ImageApi
): Adapter {
    fun spotifyToRoom(spotifyPlaylist: SpotifyPlaylist?): RoomPlaylist? {
        if(spotifyPlaylist == null)
            return null

        return RoomPlaylist(
            spotifyPlaylist.playlistId,
            spotifyPlaylist.name,
            spotifyPlaylist.description,
            spotifyPlaylist.images[0].url,
            spotifyPlaylist.uriString
        )
    }

    fun spotifyToPlaylist(spotifyPlaylist: SpotifyPlaylist?): Playlist? {
        if(spotifyPlaylist == null)
            return null

        val image = imageApi.getBitmapFromUrl(spotifyPlaylist.images[0].url)
        return Playlist(
            spotifyPlaylist.playlistId,
            spotifyPlaylist.name,
            spotifyPlaylist.description,
            image,
            Uri.parse(spotifyPlaylist.uriString)
        )
    }

    fun roomToPlaylist(roomPlaylist: RoomPlaylist?): Playlist? {
        if(roomPlaylist == null)
            return null

        val image = imageApi.getBitmapFromUrl(roomPlaylist.imageString)
        return Playlist(
            roomPlaylist.playlistId,
            roomPlaylist.name,
            roomPlaylist.description,
            image,
            Uri.parse(roomPlaylist.uriString)
        )
    }
}