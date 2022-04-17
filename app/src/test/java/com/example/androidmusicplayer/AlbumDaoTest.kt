package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.album.RoomAlbum
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class AlbumDaoTest: DaoTest() {
    private val album = RoomAlbum("1","test", "/test", "/test")

    @Test
    @Throws(IOException::class)
    fun insertAlbum() = coroutineTest {
        albumDao.insert(album)
        val res = albumDao.getById("1")
        assert(res?.name == album.name)
    }

    @Test
    @Throws(IOException::class)
    fun deleteAlbum() = coroutineTest {
        albumDao.insert(album)
        var res = albumDao.getByName("test")
        assert(res != null)
        albumDao.delete(res!!)
        res = albumDao.getByName("test")
        assert(res != album)
    }

    @Test
    @Throws(IOException::class)
    fun updateAlbum() = coroutineTest {
        albumDao.insert(album)
        val res = albumDao.getByName("test")
        assert(res != null)
        res!!.name = "updated"
        albumDao.update(res)
        assert(res != album)
        assert(res.name == "updated")
    }
}