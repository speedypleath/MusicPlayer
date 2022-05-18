package com.example.androidmusicplayer.model.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapter.AlbumAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import org.koin.core.component.inject


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
    @delegate:Ignore
    private val adapter: AlbumAdapter by inject()
}