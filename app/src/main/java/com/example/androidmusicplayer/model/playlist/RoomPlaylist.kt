package com.example.androidmusicplayer.model.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapter.PlaylistAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import org.koin.core.component.inject

@Entity(tableName = RoomPlaylist.TABLE_NAME)
data class RoomPlaylist(
    @PrimaryKey var playlistId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "image") var imageString: String?,
    @ColumnInfo(name = "uri") var uriString: String,
) : RoomModel<Playlist>, Model<Playlist> {
    companion object {
        const val TABLE_NAME = "playlist"
    }
    @Ignore
    val type = "room"
    @delegate:Ignore
    private val adapter: PlaylistAdapter by inject()

    override fun fromRoom(): Model<Playlist>? {
        return adapter.roomToPlaylist(this)
    }
}