package com.example.androidmusicplayer

//import android.app.Activity
//import android.app.Instrumentation
//import android.content.Intent
//import androidx.activity.ComponentActivity
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.test.annotation.UiThreadTest
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.example.androidmusicplayer.ui.MainActivity
//import com.spotify.sdk.android.auth.LoginActivity
//import junit.framework.TestCase
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.koin.test.KoinTest
//import org.robolectric.Robolectric
//import org.robolectric.Shadows
//
//
//@RunWith(AndroidJUnit4::class)
//class SpotifyLoginTest: KoinTest, DataStoreTest() {
//    @get:Rule
//    val composeTestRule = createComposeRule()
//    lateinit var activity: Activity
//    @Throws(Exception::class)
//    fun setUp() {
//        click()
//    }
//
//    @Throws(Exception::class)
//    fun tearDown() {
//        activity.finish()
//
//    }
//    val context: MainApp = ApplicationProvider.getApplicationContext()
//
//    @Test
//    fun pressLogin() = coroutineTest {
//        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().start().resume().get()
//        val expectedIntent = Intent(activity, LoginActivity::class.java)
//        val actual: Intent = Shadows.shadowOf(context).nextStartedActivity
//        assert(expectedIntent.component == actual.component)
//    }
//
//    @UiThreadTest
//    fun doLogin() = coroutineTest {
//        val result: Instrumentation.ActivityResult = Instrumentation.ActivityResult(
//            ComponentActivity.RESULT_OK, null)
//        TestCase.assertNotNull(result)
//
//        val am = Instrumentation.ActivityMonitor(LoginActivity::class.java.name, result, true)
//        TestCase.assertNotNull(am)
//
//        val childActivity: Activity = InstrumentationRegistry.getInstrumentation()
//            .waitForMonitorWithTimeout(am, 5000)
//        TestCase.assertNotNull(childActivity)
//
//    }
//}
//

