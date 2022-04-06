package com.example.androidmusicplayer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.song.SongDao
import com.example.androidmusicplayer.model.Song
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SongDaoTest {
    private lateinit var songDao: SongDao
    private lateinit var db: AppDatabase
    private val testSongs: List<Song> = listOf(
        Song(
            "1",
            "Test 1",
            "Test 1",
            "1",
            "1",
            0,
            "/test1"
        ),
        Song(
            "2",
            "Test 2",
            "Test 2",
            "2",
            "2",
            0,
            "/test2"
        ),
        Song(
            "3",
            "Test 2",
            "Test 2",
            "3",
            "3",
            0,
            "/test3"
        )
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        songDao = db.songDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun insertSongs() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByName("Test 1")
        assert(res.contains(testSongs[0]))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun deleteSongs() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val songToDelete = songDao.getByName("Test 1")[0]
        songDao.deleteAll(songToDelete)
        val res = songDao.getByName("Test 1")
        assert(!res.contains(testSongs[0]))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun updateSong() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val songToUpdate = songDao.getByName("Test 1")[0]
        songToUpdate.title = "updated"
        songDao.updateAll(songToUpdate)
        val res = songDao.getByName("updated")
        assert(res[0].title == "updated")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getByUri() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByUri("/test2")
        assert(res.title == "Test 2")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getByArtist() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByArtist("Test 2")
        assert(res.contains(testSongs[2]))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getByAlbum() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByAlbum("2")
        assert(res.contains(testSongs[1]))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(IOException::class)
    fun getByGenre() = runTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByGenre("3")
        assert(res.contains(testSongs[2]))
    }
}
