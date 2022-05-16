package com.example.androidmusicplayer

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.kotlin.mock
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class ProvideSpotifyApiTest: AutoCloseKoinTest() {
    @Test
    fun shouldBeSingleton() {
        val scenario = launchActivity<MainActivity>()
            scenario.moveToState(Lifecycle.State.CREATED).onActivity {
                it.spotifyApi.loadEndpoints("test-token")
                assertTrue(it.spotifyApi.isInitialized)
            }
        }

    @Test
    fun canBeInstantiated() {
        val activity = buildActivity(MainActivity::class.java).create().get()
        val spotifyApi = SpotifyApi()
        val launcher = mock<ActivityResultLauncher<Intent>>()
        val request = mock<AuthorizationRequest>()
        val intent = AuthorizationClient.createLoginActivityIntent(activity, request)
        spotifyApi.registerLauncher(launcher, intent)
        assertNotNull(spotifyApi)
        assertFalse(spotifyApi.isInitialized)
        val clone = get<SpotifyApi>()
        assertNotEquals(spotifyApi, clone)
    }
}