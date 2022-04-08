package com.example.androidmusicplayer.model.artist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.androidmusicplayer.model.song.Song

data class ArtistWithSongs(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "artistId",
        entityColumn = "songId",
        associateBy = Junction(Artist::class)
    )
    val songs: List<Song>
)