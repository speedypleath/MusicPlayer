package com.example.androidmusicplayer.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistSongCrossRef(
    val playlistId: String,
    val songId: String
)
