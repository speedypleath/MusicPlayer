package com.example.androidmusicplayer

import com.example.androidmusicplayer.util.SPOTIFY_TOKEN
import kotlinx.coroutines.flow.first
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class RetrieveTokenTest: DataStoreTest() {
//    @Test
//    fun tokenIsRetrievedTest() {
//        val controller = launchActivity<MainActivity>()
//
//        controller.moveToState(Lifecycle.State.DESTROYED)
//
//        coroutineTest {
//            val preferences: androidx.datastore.preferences.core.Preferences =
//                dataStore.data.first()
//            assertNotNull(preferences[SPOTIFY_TOKEN])
//        }
//    }

    @Test
    fun tokenIsEmpty() {
        coroutineTest {
            val preferences = dataStore.data.first()
            assertNull(preferences[SPOTIFY_TOKEN])
        }
    }
}