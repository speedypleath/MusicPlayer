package com.example.androidmusicplayer.model.artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.model.interfaces.Adapter
import com.example.androidmusicplayer.model.interfaces.Model
import com.example.androidmusicplayer.model.interfaces.RoomModel

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
    @Transient
    private lateinit var adapter: ArtistAdapter

    fun bind(adapter: Adapter) {
        this.adapter = adapter as ArtistAdapter
    }

    override fun toRoom(model: Model<Artist>): RoomModel<Artist>? {
        TODO()
    }

    override fun fromRoom(model: RoomModel<Artist>): Artist? {
        return adapter.fromRoom(model as RoomArtist?)
    }
}