package com.example.androidmusicplayer.activity

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidmusicplayer.ui.component.FullscreenPlayer
import com.example.androidmusicplayer.ui.viewmodel.PlayerViewModel
import org.koin.android.ext.android.inject

class PlayerActivity : ComponentActivity() {
    private val playerViewModel: PlayerViewModel by inject()

    override fun onStart() {
        super.onStart()
        playerViewModel.init(applicationContext)
        setContent {
            FullscreenPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        playerViewModel.releaseController()
    }
}