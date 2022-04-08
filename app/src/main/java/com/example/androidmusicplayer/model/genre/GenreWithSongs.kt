package com.example.androidmusicplayer.model.genre

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.androidmusicplayer.model.relations.GenreSongCrossRef
import com.example.androidmusicplayer.model.song.RoomSong

data class GenreWithSongs(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "songId",
        associateBy = Junction(GenreSongCrossRef::class)
    )
    val songs: List<RoomSong>
)