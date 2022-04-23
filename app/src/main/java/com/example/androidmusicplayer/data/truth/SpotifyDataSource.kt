package com.example.androidmusicplayer.data.truth

import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.model.song.Song
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.await

class SpotifyDataSource(
    val spotifyApi: SpotifyApi,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchSongs(): List<Song> =
        withContext(ioDispatcher) {
            spotifyApi.endpoint.getTracks().await().items.mapNotNull { spotifySong -> spotifySong.fromSpotify() }
        }

    suspend fun getSong(songId: String) =
        withContext(ioDispatcher) {
            spotifyApi.endpoint.getSong(songId).await()
        }
}