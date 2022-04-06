package com.example.androidmusicplayer.data.mediastore

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.activity.result.ActivityResultLauncher
import com.example.androidmusicplayer.model.Song

class MediaStoreApi(
    private val context: Context,
) {
    fun requestPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun loadSongs(): MutableList<Song> {
        val songList = mutableListOf<Song>()
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
                if(artist != "<unknown>") {
                    val uri: Uri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    val picture: Bitmap? =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            try {
                                context.contentResolver.loadThumbnail(uri, Size(128, 128), null)
                            } catch (e: Exception) {
                                null
                            }
                        } else {
                            null
                        }

                    Log.d("MediaStore", "Found song: $title")

                    val song = Song(title, artist, album, null, duration, uri.encodedPath)
                    song.addUri(uri)
                    if(picture != null)
                        song.addPicture(picture)
                    songList.add(song)
                }
            }
        }

        return songList
    }
}