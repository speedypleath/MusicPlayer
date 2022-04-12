package com.example.androidmusicplayer.web

import com.example.androidmusicplayer.model.SpotifyResponse
import com.example.androidmusicplayer.model.playlist.PlaylistWithSongs
import com.example.androidmusicplayer.model.song.SpotifySong
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SpotifyApiEndpoint {
    @GET("v1/tracks/{trackId}")
    fun getSong(@Path("trackId") trackId: String): Call<SpotifySong>

    @GET("v1/me/playlists")
    fun getPlaylists(): Call<PlaylistWithSongs>

//    @GET("/playlists/{playlistId}/tracks")
//    fun getTracksOnPlaylist(@Path("playlistId") playlistId: String): Call<List<SpotifySong>>

    @PUT("v1/playlists/{playlist_id}/images")
    fun changePlaylistPicture(@Path("playlist_id") playlistId: String, @Part("image") image: RequestBody)

    @FormUrlEncoded
    @GET("v1/search")
    fun search(@FieldMap names: Map<String, String>): Call<List<SpotifySong>>

    @GET("v1/me/top/tracks")
    fun getTracks(): Call<SpotifyResponse<List<SpotifySong>>>
}