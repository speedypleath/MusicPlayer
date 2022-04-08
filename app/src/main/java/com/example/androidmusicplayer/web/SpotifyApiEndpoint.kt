package com.example.androidmusicplayer.web

import com.example.androidmusicplayer.model.playlist.PlaylistWithSongs
import com.example.androidmusicplayer.model.song.SpotifySong
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SpotifyApiEndpoint {
    @GET("/tracks/{trackId}")
    fun getSong(@Path("trackId") trackId: String): Call<SpotifySong>

    @GET("/me/playlists")
    fun getPlaylists(): Call<PlaylistWithSongs>

//    @GET("/playlists/{playlistId}/tracks")
//    fun getTracksOnPlaylist(@Path("playlistId") playlistId: String): Call<List<SpotifySong>>

    @PUT("/playlists/{playlist_id}/images")
    fun changePlaylistPicture(@Path("playlist_id") playlistId: String, @Part("image") image: RequestBody)

    @FormUrlEncoded
    @GET("/search")
    fun search(@FieldMap names: Map<String, String>): Call<List<SpotifySong>>

    @GET("/tracks")
    fun getTracks(): Call<List<SpotifySong>>
}