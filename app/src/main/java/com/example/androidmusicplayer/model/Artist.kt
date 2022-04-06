package com.example.androidmusicplayer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Artist(
    @Json(name = "id")
    @PrimaryKey var artistId: String,
    @Json(name = "name")
    @ColumnInfo(name = "name") var name: String,
    @Json(name = "genres")
    @ColumnInfo(name = "genres") var genres: List<String>? = null,
    @Image
    @Json(name = "images")
    @ColumnInfo(name = "image") var imageString: String? = null,
    @Json(name = "uri")
    @ColumnInfo(name = "uri") var uriString: String? = null,
)