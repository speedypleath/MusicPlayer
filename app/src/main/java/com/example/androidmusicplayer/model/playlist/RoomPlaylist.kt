package com.example.androidmusicplayer.model.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapters.PlaylistAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel

@Entity(tableName = RoomPlaylist.TABLE_NAME)
data class RoomPlaylist(
    @PrimaryKey var playlistId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) : RoomModel<Playlist>, Model<Playlist> {
    companion object {
        const val TABLE_NAME = "playlist"
    }
    @Ignore
    val type = "room"
    @Transient
    private lateinit var adapter: PlaylistAdapter

    fun bind(adapter: Adapter) {
        this.adapter = adapter as PlaylistAdapter
    }

    override fun toRoom(model: Model<Playlist>): RoomModel<Playlist>? {
        TODO()
    }

    override fun fromRoom(model: RoomModel<Playlist>): Playlist? {
        TODO()
    }
}