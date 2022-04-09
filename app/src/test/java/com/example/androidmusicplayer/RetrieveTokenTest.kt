package com.example.androidmusicplayer

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import com.example.androidmusicplayer.ui.MainActivity
import com.example.androidmusicplayer.util.SPOTIFY_TOKEN
import kotlinx.coroutines.flow.first
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertNotNull
import kotlin.test.assertNull


@RunWith(RobolectricTestRunner::class)
@Config(application = MainApp::class)
class RetrieveTokenTest: DataStoreTest() {
    @Test
    fun tokenIsRetrievedTest() {
        val controller = launchActivity<MainActivity>()

        controller.moveToState(Lifecycle.State.DESTROYED)

        coroutineTest {
            val preferences: androidx.datastore.preferences.core.Preferences =
                dataStore.data.first()
            assertNotNull(preferences[SPOTIFY_TOKEN])
        }
    }

    @Test
    fun tokenIsEmpty() {
        coroutineTest {
            val preferences = dataStore.data.first()
            assertNull(preferences[SPOTIFY_TOKEN])
        }
    }
}