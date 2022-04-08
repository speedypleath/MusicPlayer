package com.example.androidmusicplayer.model.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RoomPlaylist.TABLE_NAME)
data class RoomPlaylist(
    @PrimaryKey var playlistId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) {
    companion object {
        const val TABLE_NAME = "playlist"
    }
}