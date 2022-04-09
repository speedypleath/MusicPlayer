package com.example.androidmusicplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertNotNull

@RunWith(AndroidJUnit4::class)
class MediaDependencyTest: KoinTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun injectionTest() {
        assertNotNull(get<MediaStoreApi>())
        assertNotNull(get<SongRepository>())
        assertNotNull(get<MediaStoreDataSource>())
        assertNotNull(get<MainViewModel>())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadMediaStore() = runTest {
        val mediaStoreDataSource = get<MediaStoreDataSource>()
        val songs = mediaStoreDataSource.fetchSongs()

        assertNotNull(songs)
    }
}