package com.example.androidmusicplayer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.model.album.RoomAlbum
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest: DaoTest() {
    private val album = RoomAlbum("1","test", "/test", "/test")

    @Test
    @Throws(IOException::class)
    fun insertAlbum() = coroutineTest {
        albumDao.insert(album)
        val res = albumDao.getByName("test")
        assert(res.name == album.name)
    }

    @Test
    @Throws(IOException::class)
    fun deleteAlbum() = coroutineTest {
        albumDao.insert(album)
        var res = albumDao.getByName("test")
        albumDao.delete(res)
        res = albumDao.getByName("test")
        assert(res != album)
    }

    @Test
    @Throws(IOException::class)
    fun updateAlbum() = coroutineTest {
        albumDao.insert(album)
        val res = albumDao.getByName("test")
        res.name = "updated"
        albumDao.update(res)
        assert(res != album)
        assert(res.name == "updated")
    }
}