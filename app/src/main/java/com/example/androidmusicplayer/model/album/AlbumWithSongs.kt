package com.example.androidmusicplayer.model.album

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.androidmusicplayer.model.song.Song

data class AlbumWithSongs(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "albumId",
        entityColumn = "songId",
        associateBy = Junction(Album::class)
    )
    val songs: List<Song>
)