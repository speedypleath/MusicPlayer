package com.example.androidmusicplayer.model.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel

@Entity(tableName = RoomAlbum.TABLE_NAME)
data class RoomAlbum(
    @PrimaryKey var albumId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) : RoomModel<Album>, Model<Album> {
    companion object {
        const val TABLE_NAME = "album"
    }
    @Ignore
    val type = "room"
    @Transient
    private lateinit var adapter: AlbumAdapter

    fun bind(adapter: Adapter) {
        this.adapter = adapter as AlbumAdapter
    }

    override fun toRoom(model: Model<Album>): RoomModel<Album>? {
        TODO()
    }
    override fun fromRoom(model: RoomModel<Album>): Album? {
        return adapter.fromRoom(model as RoomAlbum)
    }
}