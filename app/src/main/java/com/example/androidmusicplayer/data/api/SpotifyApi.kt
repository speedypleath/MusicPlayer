package com.example.androidmusicplayer.data.api

import androidx.activity.result.ActivityResultLauncher
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.androidmusicplayer.model.song.Song
import com.example.androidmusicplayer.util.SPOTIFY_TOKEN
import com.example.androidmusicplayer.web.AuthorizationInterceptor
import com.example.androidmusicplayer.web.SpotifyApiEndpoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class SpotifyApi (
    private val dataStore: DataStore<Preferences>
): DataApi {
    lateinit var endpoint: SpotifyApiEndpoint

    private lateinit var launcher: ActivityResultLauncher<String>

    override fun registerLauncher(requestPermissionLauncher: ActivityResultLauncher<String>) {
        launcher = requestPermissionLauncher
    }

    override suspend fun requestPermission() {
        val token: String? = dataStore.data.map { preferences ->
            preferences[SPOTIFY_TOKEN]
        }.first()

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(token))
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        endpoint = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpotifyApiEndpoint::class.java)
    }

    suspend fun loadSongs(): MutableList<Song> = TODO()
}