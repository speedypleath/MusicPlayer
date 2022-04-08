package com.example.androidmusicplayer.data

import com.example.androidmusicplayer.web.SpotifyApiEndpoint

class SpotifyDataSource(
    private val spotifyApiEndpoint: SpotifyApiEndpoint
) {
    fun fetchSongs() = spotifyApiEndpoint.getTracks()
    fun getSong(songId: String) = spotifyApiEndpoint.getSong(songId)
//    fun getTracksOnPlaylist(playlistId: String) = spotifyApiEndpoint.getTracksOnPlaylist(playlistId)
}