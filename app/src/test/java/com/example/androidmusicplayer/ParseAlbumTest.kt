package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.Album
import com.example.androidmusicplayer.web.adapters.ImageAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test
import java.io.File
import java.io.InputStream

class ParseAlbumTest {
    @Test
    fun parseAlbum() {
        val moshi = Moshi.Builder()
            .add(ImageAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<Album> = moshi.adapter(Album::class.java)

        val inputStream: InputStream = File("mock/album.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.albumId != null)
        assert(res?.name != null)
        assert(res?.uriString != null)
        assert(res?.imageString != null)
    }
}