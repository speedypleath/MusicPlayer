package com.example.androidmusicplayer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Album(
    @Json(name = "id")
    @PrimaryKey var albumId: String,
    @Json(name = "name")
    @ColumnInfo(name = "name") var name: String,
    @Image
    @Json(name = "images")
    @ColumnInfo(name = "image") var imageString: String?,
    @Json(name = "uri")
    @ColumnInfo(name = "uri") var uriString: String?,
)