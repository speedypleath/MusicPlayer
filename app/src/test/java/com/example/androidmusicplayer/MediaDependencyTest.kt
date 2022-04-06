package com.example.androidmusicplayer

import com.example.androidmusicplayer.data.mediastore.MediaStoreApi
import com.example.androidmusicplayer.data.mediastore.MediaStoreDataSource
import com.example.androidmusicplayer.data.song.SongRepository
import com.example.androidmusicplayer.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import kotlin.test.assertNotNull

class MediaDependencyTest: KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun injectionTest() {
        val mediaStoreMock = declareMock<MediaStoreApi>()

        assertNotNull(get<MediaStoreApi>())
        assertNotNull(get<SongRepository>())
        assertNotNull(get<MediaStoreDataSource>())
        assertNotNull(get<MainViewModel>())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadMediaStore() = runTest {
        val mediaStoreMock = declareMock<MediaStoreApi>()
        val mediaStoreDataSource = get<MediaStoreDataSource>()
        val songs = mediaStoreDataSource.fetchSongs()

        assertNotNull(songs)
    }
}