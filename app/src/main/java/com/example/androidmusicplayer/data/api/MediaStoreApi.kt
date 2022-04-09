package com.example.androidmusicplayer.data.api

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.androidmusicplayer.model.song.MediaStoreSong

class MediaStoreApi(
    private val context: Context,
): DataApi {
    private lateinit var launcher: ActivityResultLauncher<String>

    override fun registerLauncher(requestPermissionLauncher: ActivityResultLauncher<String>) {
        launcher = requestPermissionLauncher
    }

    override suspend fun requestPermission() {
        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    suspend fun loadSongs(): MutableList<MediaStoreSong> {
        if(this::launcher.isInitialized)
            requestPermission()
        val songList = mutableListOf<MediaStoreSong>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media._ID,
        )

        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

        val query = collection.let {
            context.contentResolver.query(
                it,
                projection,
                selection,
                null,
                null
            )
        }
        DocumentsContract.Document.COLUMN_DISPLAY_NAME
        query?.use { cursor ->
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            Log.d("MediaStore", "Query songs ${query.count}")
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
                val duration = cursor.getLong(durationColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                if(artist != "<unknown>") {
                    val song = MediaStoreSong(id, title, artist, album, duration, uri)
                    Log.d("MediaStore", "Found song: $title")
                    songList.add(song)
                }
            }
        }

        return songList
    }
}