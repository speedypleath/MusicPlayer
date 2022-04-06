package com.example.androidmusicplayer.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.web.annotations.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Playlist(
    @Json(name = "id")
    @PrimaryKey var playlistId: String,
    @Json(name = "name")
    @ColumnInfo(name = "name") var name: String,
    @Json(name = "description")
    @ColumnInfo(name = "description") var description: String?,
    @Image
    @Json(name = "images")
    @ColumnInfo(name = "image") var imageString: String?,
    @Json(name = "uri")
    @ColumnInfo(name = "uri") var uriString: String?,
) {
    constructor(name: String): this("", name, null, null, null)

    fun getPicture() {

    }

    fun getUri(): Uri? {
        if(uriString != null)
            return Uri.parse(uriString)
        return null
    }
}
