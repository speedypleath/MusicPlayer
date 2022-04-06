package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.Artist
import com.example.androidmusicplayer.web.adapters.ImageAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test
import java.io.File
import java.io.InputStream

class ParseArtistTest {
    @Test
    fun parseArtist() {
        val moshi = Moshi.Builder()
            .add(ImageAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<Artist> = moshi.adapter(Artist::class.java)

        val inputStream: InputStream = File("mock/artist.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.name != null)
        assert(res?.genres != null)
        assert(res?.uriString != null)
        assert(res?.imageString != null)
        assert(res?.artistId != null)
    }
}