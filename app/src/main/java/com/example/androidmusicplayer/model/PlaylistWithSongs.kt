package com.example.androidmusicplayer.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.androidmusicplayer.data.playlist.PlaylistSongCrossRef

data class PlaylistWithSongs(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val songs: List<Song>
)