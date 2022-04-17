package com.example.androidmusicplayer

import com.example.androidmusicplayer.model.artist.RoomArtist
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class ArtistDaoTest: DaoTest() {
    private val artist = RoomArtist("1","test", "/test", "/test")

    @Test
    @Throws(IOException::class)
    fun insertArtist() = coroutineTest {
        artistDao.insert(artist)
        val res = artistDao.getById("1")
        assert(res != null)
        assert(res?.name == artist.name)
    }

    @Test
    @Throws(IOException::class)
    fun deleteArtist() = coroutineTest {
        artistDao.insert(artist)
        var res = artistDao.getByName("test")
        assert(res != null)
        artistDao.delete(res!!)
        res = artistDao.getByName("test")
        assert(res != artist)
    }

    @Test
    @Throws(IOException::class)
    fun updateArtist() = coroutineTest {
        artistDao.insert(artist)
        val res = artistDao.getByName("test")
        assert(res != null)
        res?.name = "updated"
        artistDao.update(res!!)
        assert(res != artist)
        assert(res.name == "updated")
    }
}