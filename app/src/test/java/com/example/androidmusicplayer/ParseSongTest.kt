package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.Song
import com.example.androidmusicplayer.web.adapters.AlbumAdapter
import com.example.androidmusicplayer.web.adapters.AlbumToImageAdapter
import com.example.androidmusicplayer.web.adapters.ArtistAdapter
import com.example.androidmusicplayer.web.adapters.ImageAdapter
import com.serjltt.moshi.adapters.FirstElement
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Test
import java.io.File
import java.io.InputStream

class ParseSongTest {
    @Test
    fun parseSong() {
        val moshi = Moshi.Builder()
            .add(ImageAdapter())
            .add(AlbumAdapter())
            .add(ArtistAdapter())
            .add(AlbumToImageAdapter())
            .add(FirstElement.ADAPTER_FACTORY)
            .add(Wrapped.ADAPTER_FACTORY)
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter: JsonAdapter<Song> = moshi.adapter(Song::class.java)

        val inputStream: InputStream = File("mock/song.json").inputStream()

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val res = jsonAdapter.fromJson(jsonString)
        assert(res?.title != null)
        assert(res?.length != null)
        assert(res?.uriString != null)
        assert(res?.imageString != null)
        assert(res?.songId != null)
        assert(res?.album != null)
    }
}