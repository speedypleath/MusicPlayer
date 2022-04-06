package com.example.androidmusicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true) var playlistId: Long,
    var name: String
) {
    constructor(name: String): this(0, name)
}
