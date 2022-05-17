package com.example.androidmusicplayer.activity

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidmusicplayer.ui.component.Playlist
import com.example.androidmusicplayer.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistActivity : ComponentActivity() {
    private val playlistViewModel: PlaylistViewModel by viewModel()

    companion object {
        const val MEDIA_ITEM_ID_KEY = "MEDIA_ITEM_ID_KEY"
        fun createIntent(context: Context, mediaItemID: String): Intent {
            val intent = Intent(context, PlaylistActivity::class.java)
            intent.putExtra(MEDIA_ITEM_ID_KEY, mediaItemID)
            return intent
        }
    }

    private fun openPlayer() {
        val intent = Intent(this, PlayerActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        playlistViewModel.init(applicationContext, intent) { openPlayer() }
        setContent {
            Playlist(playlistViewModel)
        }
    }

    override fun onStop() {
        super.onStop()
        playlistViewModel.releaseBrowser()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}