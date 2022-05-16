package com.example.androidmusicplayer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.web.SpotifyApiEndpoint
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.LoginActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.kotlin.mock
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class SpotifyTest: KoinTest, DataStoreTest() {
    val app = ApplicationProvider.getApplicationContext() as AndroidMusicPlayer
    val spotifyApi: SpotifyApi by inject()
    lateinit var activity: MainActivity
    @Before
    fun setup() {
        activity = buildActivity(MainActivity::class.java).setup().get()
        assertNotNull(activity)
        assertNotNull(spotifyApi)
        assertFalse(spotifyApi.isInitialized)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun shouldThrowUninitializedPropertyAccessException() {
        spotifyApi.endpoint
    }

    @Test
    fun launcherShouldBeInitialized() {
        spotifyApi.loadEndpoints("test-token")
        assertTrue(spotifyApi.isInitialized)
    }

    @Test
    fun shouldLaunchActivity() = coroutineTest {
        spotifyApi.requestPermission()
        val expectedIntent = Intent(activity, LoginActivity::class.java)
        assertNotNull(expectedIntent)
        assertNotNull(SpotifyApiEndpoint::class.java)
        val actual: Intent = shadowOf(app).nextStartedActivity
        assertEquals(expectedIntent.component, actual.component)
    }

    @Test
    fun shouldFinishLoginActivityIfNoAuthRequest() {
        val intent = Intent(activity, LoginActivity::class.java)
        val loginActivity: Activity = buildActivity(LoginActivity::class.java, intent).create().get()
        assertTrue(loginActivity.isFinishing)
        assertEquals(Activity.RESULT_CANCELED, shadowOf(loginActivity).resultCode)
    }

    @Test
    fun shouldFinishLoginActivityWhenCompleted() {
        val request =
            AuthorizationRequest.Builder("test", AuthorizationResponse.Type.TOKEN, "test://test")
                .build()
        val response = mock<AuthorizationResponse>()

        val bundle = Bundle()
        bundle.putParcelable(LoginActivity.REQUEST_KEY, request)

        val intent = AuthorizationClient.createLoginActivityIntent(activity, request)
        intent.putExtras(bundle)
        val loginActivityActivityController = buildActivity(
            LoginActivity::class.java, intent
        )

        val loginActivity = loginActivityActivityController.get()
        val shadowLoginActivity = shadowOf(loginActivity)
        shadowLoginActivity.setCallingActivity(activity.componentName)
        loginActivityActivityController.create()
        assertFalse(loginActivity.isFinishing)
        loginActivity.onClientComplete(response)
        assertTrue(loginActivity.isFinishing)
        assertEquals(Activity.RESULT_OK, shadowLoginActivity.resultCode)
        val temp: Bundle = shadowLoginActivity.resultIntent.extras?.get(
            shadowLoginActivity.resultIntent.extras?.keySet()?.parallelStream()?.findFirst()
                ?.get() ?: ""
        ) as Bundle
        assertEquals(
            response,
            temp.get(LoginActivity.RESPONSE_KEY)
        )
    }

    @Test
    fun shouldLoadEndpoints() {
        spotifyApi.loadEndpoints("test-token")
        assertTrue(spotifyApi.isInitialized)
        assertNotNull(spotifyApi.endpoint)
    }

    @Test
    fun shouldNotLoadMultipleTimes() {
        spotifyApi.loadEndpoints("test-token")
        val endpoint = spotifyApi.endpoint
        spotifyApi.loadEndpoints("test-token")
        assertEquals(spotifyApi.endpoint.toString(), endpoint.toString())
    }
}

