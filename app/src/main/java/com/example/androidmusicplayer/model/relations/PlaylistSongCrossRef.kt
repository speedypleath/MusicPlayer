package com.example.androidmusicplayer.model.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistSongCrossRef(
    @ColumnInfo(name = "playlistId", index = true) val playlistId: String,
    @ColumnInfo(name = "songId", index = true) val songId: String
)
