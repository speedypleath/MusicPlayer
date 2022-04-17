package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.song.RoomSong
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class SongDaoTest: DaoTest() {
    private val testSongs: List<RoomSong> = listOf(
        RoomSong(
            "1",
            "Test 1",
            "Test 1",
            "1",
            0,
            "/test1",
            "/test1"
        ),
        RoomSong(
            "2",
            "Test 2",
            "Test 2",
            "2",
            0,
            "/test2",
            "/test2"
        ),
        RoomSong(
            "3",
            "Test 2",
            "Test 2",
            "3",
            0,
            "/test3",
            "/test3"
        )
    )

    @Test
    @Throws(IOException::class)
    fun insertSongs() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByName("Test 1")
        assert(res != null)
        assert(res!!.contains(testSongs[0]))
    }

    @Test
    @Throws(IOException::class)
    fun deleteSongs() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val songToDelete = songDao.getByName("Test 1")?.get(0)
        assert(songToDelete != null)
        songDao.deleteAll(songToDelete!!)
        val res = songDao.getByName("Test 1")
        assert(res != null)
        assert(!res!!.contains(testSongs[0]))
    }

    @Test
    @Throws(IOException::class)
    fun updateSong() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val songToUpdate = songDao.getByName("Test 1")?.get(0)
        assert(songToUpdate != null)
        songToUpdate!!.title = "updated"
        songDao.updateAll(songToUpdate)
        val res = songDao.getByName("updated")
        assert(res != null)
        assert(res?.get(0)!!.title == "updated")
    }

    @Test
    @Throws(IOException::class)
    fun getByUri() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByUri("/test2")
        assert(res != null)
        assert(res!!.title == "Test 2")
    }

    @Test
    @Throws(IOException::class)
    fun getByArtist() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByArtist("Test 2")
        assert(res != null)
        assert(res!!.contains(testSongs[2]))
    }

    @Test
    @Throws(IOException::class)
    fun getByAlbum() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
        val res = songDao.getByAlbum("2")
        assert(res != null)
        assert(res!!.contains(testSongs[1]))
    }

    @Test
    @Throws(IOException::class)
    fun getByGenre() = coroutineTest {
        songDao.insertAll(testSongs[0], testSongs[1], testSongs[2])
//        val res = songDao.getByGenre("3")
//        assert(res.contains(testSongs[2]))
    }
}
