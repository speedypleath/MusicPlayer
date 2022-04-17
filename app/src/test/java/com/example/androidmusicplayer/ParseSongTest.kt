package com.example.androidmusicplayer

//import com.example.androidmusicplayer.adapters.ArtistAdapter
//import com.example.androidmusicplayer.model.song.SpotifySong
//import com.serjltt.moshi.adapters.FirstElement
//import com.squareup.moshi.JsonAdapter
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import org.junit.Test
//import java.io.File
//import java.io.InputStream
//
//class ParseSongTest {
//    @Test
//    fun parseSong() {
//        val moshi = Moshi.Builder()
//            .add(ArtistAdapter())
//            .add(FirstElement.ADAPTER_FACTORY)
//            .addLast(KotlinJsonAdapterFactory())
//            .build()
//
//        val jsonAdapter: JsonAdapter<SpotifySong> = moshi.adapter(SpotifySong::class.java)
//
//        val inputStream: InputStream = File("mock/song.json").inputStream()
//
//        val jsonString = inputStream.bufferedReader().use { it.readText() }
//
//        val res = jsonAdapter.fromJson(jsonString)
//        assert(res?.title != null)
//        assert(res?.length != null)
//        assert(res?.album?.name != null)
//        assert(res?.songId != null)
//        assert(res?.album?.images != null)
//    }
//}