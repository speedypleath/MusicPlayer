package com.example.androidmusicplayer.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.androidmusicplayer.model.relations.PlaylistSongCrossRef
import com.example.androidmusicplayer.model.song.RoomSong

data class PlaylistWithSongs(
    @Embedded val playlist: RoomPlaylist,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val songs: List<RoomSong>
)