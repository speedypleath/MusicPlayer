package com.example.androidmusicplayer

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.dao.AlbumDao
import com.example.androidmusicplayer.data.dao.ArtistDao
import com.example.androidmusicplayer.data.dao.PlaylistDao
import com.example.androidmusicplayer.data.dao.SongDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.test.AutoCloseKoinTest
import java.io.IOException

abstract class DaoTest(
    coroutineTestImpl: CoroutineTest = CoroutineTestImpl()
): CoroutineTest by coroutineTestImpl, AutoCloseKoinTest() {
    private lateinit var db: AppDatabase
    protected lateinit var artistDao: ArtistDao
    protected lateinit var albumDao: AlbumDao
    protected lateinit var songDao: SongDao
    protected lateinit var playlistDao: PlaylistDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        artistDao = db.artistDao()
        albumDao = db.albumDao()
        songDao = db.songDao()
        playlistDao = db.playlistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}