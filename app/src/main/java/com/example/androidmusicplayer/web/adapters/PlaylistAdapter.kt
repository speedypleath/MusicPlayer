package com.example.androidmusicplayer.web.adapters

import android.net.Uri
import com.example.androidmusicplayer.ImageApi
import com.example.androidmusicplayer.model.playlist.Playlist
import com.example.androidmusicplayer.model.playlist.RoomPlaylist
import com.example.androidmusicplayer.model.playlist.SpotifyPlaylist

class PlaylistAdapter(
    private val imageApi: ImageApi
) {
    fun spotifyToRoom(spotifyPlaylist: SpotifyPlaylist): RoomPlaylist {
        return RoomPlaylist(
            spotifyPlaylist.playlistId,
            spotifyPlaylist.name,
            spotifyPlaylist.description,
            spotifyPlaylist.imageString,
            spotifyPlaylist.uriString
        )
    }

    fun spotifyToPlaylist(spotifyPlaylist: SpotifyPlaylist): Playlist {
        val image = imageApi.getBitmapFromUrl(spotifyPlaylist.imageString)
        return Playlist(
            spotifyPlaylist.playlistId,
            spotifyPlaylist.name,
            spotifyPlaylist.description,
            image,
            Uri.parse(spotifyPlaylist.uriString)
        )
    }

    fun roomToPlaylist(roomPlaylist: RoomPlaylist): Playlist {
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