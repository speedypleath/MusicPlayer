package com.example.androidmusicplayer.model.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapter.ArtistAdapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel
import org.koin.core.component.inject

@Entity(tableName = RoomArtist.TABLE_NAME)
data class RoomArtist(
    @PrimaryKey var artistId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String,
) : RoomModel<Artist>, Model<Artist> {
    companion object {
        const val TABLE_NAME = "artist"
    }
    @Ignore
    val type = "room"
    @delegate:Ignore
    private val adapter: ArtistAdapter by inject()

    fun fromRoom(): Artist {
        return adapter.fromRoom(this)
    }
}