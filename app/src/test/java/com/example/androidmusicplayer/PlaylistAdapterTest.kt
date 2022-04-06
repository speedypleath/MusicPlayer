package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.Playlist
import com.example.androidmusicplayer.web.adapters.ImageAdapter
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
            .add(ImageAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<Playlist> = moshi.adapter(Playlist::class.java)

        val inputStream: InputStream = File("mock/playlist.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.playlistId != null)
        assert(res?.name != null)
        assert(res?.description != null)
        assert(res?.uriString != null)
        assert(res?.imageString != null)
    }
}