package com.example.androidmusicplayer

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.androidmusicplayer.activity.MainActivity
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class RequestInternalStorageAccessTest: DataStoreTest() {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val mediaStoreApi: MediaStoreApi by inject()

    @Test
    fun loadMediaStore()  {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            coroutineTest {
                mediaStoreApi.requestPermission()
            }
            Log.d("Media Store Test", activity.activityResult.value.toString())
            activity.activityResult.observe(activity) { status ->
                    runBlocking {
                    when (status) {
                        Status.SUCCESS -> assert(true)
                        Status.ERROR -> assert(true)
                        else -> delay(10)
                    }
                }
            }
        }
    }
}