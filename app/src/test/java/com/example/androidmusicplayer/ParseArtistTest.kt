package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.artist.SpotifyArtist
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
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<SpotifyArtist> = moshi.adapter(SpotifyArtist::class.java)

        val inputStream: InputStream = File("mock/artist.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.name != null)
        assert(res?.genres != null)
        assert(res?.uriString != null)
        assert(res?.images != null)
        assert(res?.artistId != null)
    }
}