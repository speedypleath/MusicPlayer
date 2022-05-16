package com.example.androidmusicplayer

import com.example.androidmusicplayer.data.repository.SongRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.get
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidMusicPlayer::class)
class RepositoryTest: DataStoreTest() {
    @Test
    fun createDatabase() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val songRepository: SongRepository = get()
        songRepository.createDb(activity.applicationContext)
    }

//    @Test
//    fun getSongs() = coroutineTest {
//        Robolectric.buildActivity(MainActivity::class.java).create().get()
//        val songRepository: SongRepository = get()
//        val spotifyApi: SpotifyApi = get()
//        val songs = songRepository.fetchSongs(false)
//        assertNotEquals(songs.size, 0)
//    }
}