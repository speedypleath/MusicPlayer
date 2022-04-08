package com.example.androidmusicplayer.model.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RoomArtist.TABLE_NAME)
data class RoomArtist(
    @PrimaryKey var artistId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) {
    companion object {
        const val TABLE_NAME = "artist"
    }
}