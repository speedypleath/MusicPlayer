package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.playlist.RoomPlaylist
import org.junit.Test
import java.io.IOException

class PlaylistDaoTest: DaoTest() {
    private val testPlaylist = RoomPlaylist("1","test", "test", "/test", "/test")

    @Test
    @Throws(IOException::class)
    fun insertPlaylist() = coroutineTest {
        playlistDao.insert(testPlaylist)
        val res = playlistDao.getByName("test")
        assert(res != null)
        assert(res?.name == testPlaylist.name)
    }

    @Test
    @Throws(IOException::class)
    fun deletePlaylist() = coroutineTest {
        playlistDao.insert(testPlaylist)
        var res = playlistDao.getByName("test")
        assert(res != null)
        playlistDao.delete(res!!)
        res = playlistDao.getByName("test")
        assert(res != testPlaylist)
    }

    @Test
    @Throws(IOException::class)
    fun updatePlaylist() = coroutineTest {
        playlistDao.insert(testPlaylist)
        val res = playlistDao.getByName("test")
        assert(res != null)
        res?.name = "updated"
        playlistDao.update(res!!)
        assert(res != testPlaylist)
        assert(res.name == "updated")
    }
}