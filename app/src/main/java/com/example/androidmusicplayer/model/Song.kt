package com.example.androidmusicplayer.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true) var songId: Long,
    @ColumnInfo(name = "title", index = true) var title: String,
    @ColumnInfo(name = "artist", index = true) var artist: String,
    @ColumnInfo(name = "album", index = true) var album: String?,
    @ColumnInfo(name = "genre") var genre: String?,
    @ColumnInfo(name = "length") var length: Long,
    @ColumnInfo(name = "uri") var uriString: String?,
    @Ignore var picture: Bitmap?,
    @Ignore var uri: Uri?
) {
    constructor(title: String, artist: String, album: String?, genre: String?, length: Long, uriString: String?)
        : this(0, title, artist, album, genre, length, uriString, null, null)

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Song -> {
                title == other.title && artist == other.artist && album == other.album && genre == other.genre && length == other.length && other.picture == other.picture
            }
            else -> false
        }
    }

    override fun hashCode(): Int{
        return hashCode()
    }

    fun addUri(uri: Uri) {
        this.uri = Uri.parse(uriString)
    }

    fun addPicture(picture: Bitmap){
        this.picture = picture
    }
}