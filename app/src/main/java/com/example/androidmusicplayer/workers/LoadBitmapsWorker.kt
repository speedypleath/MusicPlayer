package com.example.androidmusicplayer.workers

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.androidmusicplayer.data.api.ImageApi
import com.example.androidmusicplayer.data.repository.SongRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoadBitmapsWorker(
    appContext: Context,
    workerParams: WorkerParameters
): Worker(appContext, workerParams), KoinComponent {
    private val songRepository: SongRepository by inject()
    private val imageApi: ImageApi by inject()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun doWork(): Result {
        songRepository.songs.stream().forEach { song ->
            val retriever = MediaMetadataRetriever()
            try {
                Log.d("Worker", song.title)
                Log.d("Worker", song.uri.toString())
                song.image = applicationContext.contentResolver.loadThumbnail( Uri.withAppendedPath(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, song.album!!.albumId), Size(128, 128), null)
            } catch (e: Exception) {
                Log.d("Worker", e.stackTraceToString())
                song.image = song.imageUri?.let { imageApi.getBitmapFromUrl(it) }
            } finally {
                try {
                    retriever.release();
                } catch (e2: Exception) {
                    e2.printStackTrace();
                }
            }
        }
        return Result.success()
    }
}

