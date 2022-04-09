package com.example.androidmusicplayer.data.api

import androidx.activity.result.ActivityResultLauncher

interface DataApi {
    fun registerLauncher(requestPermissionLauncher: ActivityResultLauncher<String>)
    suspend fun requestPermission()
}