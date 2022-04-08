package com.example.androidmusicplayer

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.data.mediastore.MediaStoreApi
import com.example.androidmusicplayer.ui.MainActivity
import com.example.androidmusicplayer.util.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class RequestInternalStorageAccessTest: KoinTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mediaStoreApi: MediaStoreApi by inject()

    @Test
    fun loadMediaStore() {
        val scenario = activityScenarioRule.scenario
        scenario.onActivity { activity ->
            mediaStoreApi.requestPermission()
            Log.d("Media Store Test", activity.activityResult.value.toString())
            activity.activityResult.observe(activity) { status ->
                    runBlocking {
                    when (status) {
                        Status.SUCCESS -> assert(true)
                        Status.ERROR -> assert(false)
                        else -> delay(10)
                    }
                }
            }
        }
    }
}