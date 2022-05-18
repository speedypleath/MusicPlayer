package com.example.androidmusicplayer.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidmusicplayer.data.repository.SongRepository
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoadSongsWorker(
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams), KoinComponent {
    private val songRepository: SongRepository by inject()
    override suspend fun doWork(): Result {
        runBlocking {
            songRepository.fetchSongs()
        }

        return Result.success()
    }
}