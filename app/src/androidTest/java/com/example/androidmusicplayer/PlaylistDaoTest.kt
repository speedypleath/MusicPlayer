package com.example.androidmusicplayer

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.model.Playlist
import com.example.androidmusicplayer.data.playlist.PlaylistDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlaylistDaoTest {
    private lateinit var playlistDao: PlaylistDao
    private lateinit var db: AppDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        playlistDao = db.playlistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun insertPlaylist() = runTest {
        val testPlaylist = Playlist("test")
        playlistDao.insert(testPlaylist)
        val res = playlistDao.getByName("test")
        assert(res.name == testPlaylist.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun deletePlaylist() = runTest {
        val testPlaylist = Playlist("test")
        playlistDao.insert(testPlaylist)
        val res = playlistDao.getByName("test")
        playlistDao.delete(res)
        assert(res != testPlaylist)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun updatePlaylist() = runTest {
        val testPlaylist = Playlist("test")
        playlistDao.insert(testPlaylist)
        val res = playlistDao.getByName("test")
        res.name = "updated"
        playlistDao.update(res)
        assert(res != testPlaylist)
        assert(res.name == "updated")
    }
}