package com.example.androidmusicplayer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.web.annotations.Album
import com.example.androidmusicplayer.web.annotations.Artist
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class Song(
    @Json(name = "id")
    @PrimaryKey var songId: String,
    @Json(name = "name")
    @ColumnInfo(name = "title", index = true) var title: String,
    @Artist
    @Json(name = "artists")
    @ColumnInfo(name = "artist", index = true) var artist: String,
    @Album
    @ColumnInfo(name = "album", index = true) var album: String?,
    @Json(name = "duration_ms")
    @ColumnInfo(name = "length") var length: Long,
    @Album @Image
    @Json(name = "album")
    @ColumnInfo(name = "image") var imageString: String,
    @Json(name = "uri")
    @ColumnInfo(name = "uri") var uriString: String?
)