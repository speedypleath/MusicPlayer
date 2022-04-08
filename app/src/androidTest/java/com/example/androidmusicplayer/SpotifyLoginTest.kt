package com.example.androidmusicplayer

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.ui.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SpotifyLoginTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun spotifyConnectTest() = runTest {
        activityScenario.scenario.onActivity {
            it.connectToSpotify()
        }
    }
}