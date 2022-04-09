package com.example.androidmusicplayer

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.ui.MainViewModel
import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.adapters.SongAdapter
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
private val appModule = module {
    single { provideDatabase(androidContext()) }
    single { provideSpotifyDataSource(get()) }
    single { provideMediaStoreDataSource(get(), get()) }
    single { provideSongAdapter(androidContext(), get()) }
    single { SongRepository(get(), get()) }
    single { MediaStoreApi(androidContext()) }
    single { SpotifyApi(get()) }
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("preferences")
        }
    }
    factory { ImageApi(androidContext()) }
    viewModel {
        MainViewModel(get(),get())
    }
}

val testModule = module {
    appModule
    single { provideTestDatabase(androidContext()) }
}

private fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java, "samsara"
    ).build()

private fun provideSpotifyDataSource(spotifyApi: SpotifyApi): SpotifyDataSource =
    SpotifyDataSource(
        spotifyApi,
        Dispatchers.IO,
    )

private fun provideTestDatabase(context: Context): AppDatabase =
    Room.inMemoryDatabaseBuilder(
        context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()

private fun provideMediaStoreDataSource(mediaStoreApi: MediaStoreApi, songAdapter: SongAdapter): MediaStoreDataSource =
    MediaStoreDataSource(
        mediaStoreApi,
        Dispatchers.IO,
        songAdapter
    )

private fun provideSongAdapter(context: Context, appDatabase: AppDatabase): SongAdapter = SongAdapter(
    ImageApi(context),
    appDatabase.artistDao(),
    appDatabase.albumDao(),
    AlbumAdapter(),
    ArtistAdapter()
)

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            androidLogger()
            modules(appModule, testModule)
        }
        instance = this
    }

    companion object {
        lateinit var instance: MainApp
    }
}