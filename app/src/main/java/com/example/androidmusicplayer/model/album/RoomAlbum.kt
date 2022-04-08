package com.example.androidmusicplayer.model.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RoomAlbum.TABLE_NAME)
data class RoomAlbum(
    @PrimaryKey var albumId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) {
    companion object {
        const val TABLE_NAME = "album"
    }
}