package com.example.androidmusicplayer

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.androidmusicplayer.ui.MainActivity
import com.example.androidmusicplayer.util.SPOTIFY_TOKEN
import kotlinx.coroutines.flow.first
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNull

class RetrieveTokenTest: DataStoreTest() {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tokenIsEmpty() {
        coroutineTest {
            val preferences: androidx.datastore.preferences.core.Preferences = dataStore.data.first()
            assertNull(preferences[SPOTIFY_TOKEN])
        }
    }

    @Test
    fun tokenIsRetrievedTest() {
        activityScenario.scenario.onActivity {
            it.connectToSpotify()
        }
        when(activityScenario.scenario.state.isAtLeast(Lifecycle.State.DESTROYED)) { true ->
            coroutineTest {
                val preferences: androidx.datastore.preferences.core.Preferences =
                    dataStore.data.first()
                assertNull(preferences[SPOTIFY_TOKEN])
            }
            else -> {
                Log.d("Test", "Still in activity")
            }
        }
    }
}