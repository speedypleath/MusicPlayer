package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.playlist.SpotifyPlaylist
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test
import java.io.File
import java.io.InputStream

class PlaylistAdapterTest {
    @Test
    fun testAdapter() {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<SpotifyPlaylist> = moshi.adapter(SpotifyPlaylist::class.java)

        val inputStream: InputStream = File("mock/playlist.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.playlistId != null)
        assert(res?.name != null)
        assert(res?.description != null)
        assert(res?.uriString != null)
        assert(res?.images != null)
    }
}