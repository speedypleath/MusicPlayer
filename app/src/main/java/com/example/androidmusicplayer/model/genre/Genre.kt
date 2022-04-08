package com.example.androidmusicplayer.model.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id") @PrimaryKey var genreId: String,
    @Json(name = "name") @ColumnInfo("name") var name: String
)