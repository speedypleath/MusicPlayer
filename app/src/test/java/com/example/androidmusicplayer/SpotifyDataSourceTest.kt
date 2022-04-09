package com.example.androidmusicplayer

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import com.example.androidmusicplayer.ui.MainActivity
import com.spotify.sdk.android.auth.LoginActivity
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implements


@RunWith(RobolectricTestRunner::class)
@Config(application = MainApp::class)
@Implements(LoginActivity::class)
class SpotifyDataSourceTest: KoinTest, DataStoreTest() {
    val app = ApplicationProvider.getApplicationContext() as MainApp

    @Test
    fun pressLogin() = coroutineTest {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().start().resume().get()
        val expectedIntent = Intent(activity, LoginActivity::class.java)
        val actual: Intent = shadowOf(app).nextStartedActivity
        assertEquals(expectedIntent.component, actual.component)
//        launchActivity<LoginActivity>(activity.spotifyIntent)
//        Instrumentation.ActivityMonitor().waitForActivity()
    }

//    @Test
//    fun login() = coroutineTest {
//        val activity = Robolectric.buildActivity(LoginActivity::class.java).setup().start().resume().get().onTopResumedActivityChanged(false)
//    }
}