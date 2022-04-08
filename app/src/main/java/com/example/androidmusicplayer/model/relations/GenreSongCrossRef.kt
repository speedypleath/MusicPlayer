package com.example.androidmusicplayer.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["genreId", "songId"])
data class GenreSongCrossRef(
    val genreId: String,
    val songId: String
)
