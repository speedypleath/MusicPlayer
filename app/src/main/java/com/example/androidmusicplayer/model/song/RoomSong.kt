package com.example.androidmusicplayer.model.song

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.model.interfaces.*


@Entity(tableName = RoomSong.TABLE_NAME)
data class RoomSong(
    @ColumnInfo(name = "songId", index = true) @PrimaryKey var songId: String,
    @ColumnInfo(name = "title", index = true) var title: String,
    @ColumnInfo(name = "artist", index = true) var artist: String,
    @ColumnInfo(name = "album", index = true) var album: String,
    @ColumnInfo(name = "length") var length: Long,
    @ColumnInfo(name = "image") var imageString: String,
    @ColumnInfo(name = "uri") var uriString: String
): RoomModel<Song>, Model<Song>, MediaStoreModel<Song>, SpotifyModel<Song> {
    companion object {
        const val TABLE_NAME = "song"
    }
    @Transient
    private lateinit var adapter: SongAdapter

    fun bind(adapter: Adapter) {
        this.adapter = adapter as SongAdapter
    }

    override fun toRoom(model: Model<Song>): RoomModel<Song>? = null

    override fun toMediaStore(model: Model<Song>): MediaStoreModel<Song>? = null

    override fun fromMediaStore(model: MediaStoreModel<Song>): Song? {
        return adapter.mediaStoreToSong(model as MediaStoreSong)
    }

    override fun fromRoom(model: RoomModel<Song>): Song? {
        return adapter.roomToSong(model as RoomSong)
    }

    override fun fromSpotify(model: SpotifyModel<Song>): Model<Song>? {
        return adapter.fromSpotify(model as SpotifySong)
    }
}