package com.example.androidmusicplayer.web

import com.example.androidmusicplayer.model.PlaylistWithSongs
import com.example.androidmusicplayer.model.Song
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SpotifyApiEndpointInterface {
    @GET("/me/playlists")
    fun getPlaylists(): Call<PlaylistWithSongs>

    @GET("/playlists/{playlistId}/tracks")
    fun getTrackOnPlaylist(@Path("playlistId") playlistId: String): Call<List<Song>>

    @PUT("/playlists/{playlist_id}/images")
    fun changePlaylistPicture(@Path("playlist_id") playlistId: String, @Part("image") image: RequestBody)

    @FormUrlEncoded
    @GET("v1/search")
    fun search(@FieldMap names: Map<String, String>): Call<List<Song>>
}