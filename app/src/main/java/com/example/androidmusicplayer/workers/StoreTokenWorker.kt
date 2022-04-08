package com.example.androidmusicplayer.workers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.work.Worker
import androidx.work.WorkerParameters

class StoreTokenWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val dataStore: DataStore<Preferences>
): Worker(context, workerParameters) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}