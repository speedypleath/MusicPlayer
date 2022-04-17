package com.example.androidmusicplayer

import android.Manifest
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.test.core.app.ApplicationProvider
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.ui.MainActivity
import com.example.androidmusicplayer.util.Status
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.get
import org.mockito.kotlin.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class MediaStoreTest: DataStoreTest() {
    @Test
    fun provideMediaStoreApiTest() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val mediaStoreApi = MediaStoreApi(activity.applicationContext)
        val launcher = mock<ActivityResultLauncher<String>>()
        mediaStoreApi.registerLauncher(launcher)
        mediaStoreApi.requestPermission()
        assertEquals(activity.activityResult.value, Status.LOADING)
    }

    @Test
    fun injectionTest() {
        Robolectric.buildActivity(MainActivity::class.java).setup()
        assertNotNull(get<MediaStoreApi>())
        assertNotNull(get<SongRepository>())
        assertNotNull(get<MediaStoreDataSource>())
    }

    @Test
    fun loadMediaStore() = coroutineTest {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        assertNotNull(get<MediaStoreApi>())
        val mediaStoreDataSource = get<MediaStoreDataSource>()
        assertNotNull(mediaStoreDataSource)
        val songs = activity.mediaStoreApi.loadSongs()
        assertNotNull(songs)
    }

    @Test
    fun launchRequest() = coroutineTest {
        val app = ApplicationProvider.getApplicationContext() as AndroidMusicPlayer
        assertNotNull(app)
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        assertNotNull(activity)
        val mediaStoreApi: MediaStoreApi = get()
        assertNotNull(mediaStoreApi)
        mediaStoreApi.requestPermission()
        val expectedIntent = Intent(Manifest.permission.READ_EXTERNAL_STORAGE)
        assertNotNull(expectedIntent)
        val actual: Intent = Shadows.shadowOf(app).nextStartedActivity
        assertEquals(expectedIntent.component, actual.component)
        assertEquals(activity.activityResult.value, Status.ERROR)
    }
}