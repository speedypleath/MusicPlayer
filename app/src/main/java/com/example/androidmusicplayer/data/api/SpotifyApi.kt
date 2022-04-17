package com.example.androidmusicplayer.data.api

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.androidmusicplayer.adapters.ResponseAdapter
import com.example.androidmusicplayer.web.AuthorizationInterceptor
import com.example.androidmusicplayer.web.SpotifyApiEndpoint
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SpotifyApi {
    lateinit var endpoint: SpotifyApiEndpoint
    val isInitialized get() = ::endpoint.isInitialized

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var intent: Intent

    fun registerLauncher(launcher: ActivityResultLauncher<Intent>, intent: Intent) {
        this.launcher = launcher
        this.intent = intent
    }

    fun requestPermission() {
        launcher.launch(intent)
    }

    fun loadEndpoints(token: String) {
        if(isInitialized)
            return

        val moshi = Moshi.Builder()
            .add(ResponseAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(token))
            .build()

        endpoint = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(SpotifyApiEndpoint::class.java)
    }
}