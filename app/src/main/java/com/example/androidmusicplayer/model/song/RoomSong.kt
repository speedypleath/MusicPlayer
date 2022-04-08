package com.example.androidmusicplayer.model.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = RoomSong.TABLE_NAME)
data class RoomSong(
    @ColumnInfo(name = "songId", index = true) @PrimaryKey var songId: String,
    @ColumnInfo(name = "title", index = true) var title: String,
    @ColumnInfo(name = "artist", index = true) var artist: String,
    @ColumnInfo(name = "album", index = true) var album: String,
    @ColumnInfo(name = "length") var length: Long,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String
) {
    companion object {
        const val TABLE_NAME = "song"
    }
}