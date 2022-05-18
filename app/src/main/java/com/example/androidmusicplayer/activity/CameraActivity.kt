package com.example.androidmusicplayer.activity

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.example.androidmusicplayer.camera.MainContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class CameraActivity: ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class, ExperimentalCoilApi::class)
    override fun onStart() {
        super.onStart()
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                MainContent(Modifier.fillMaxSize())
            }
        }
    }
}