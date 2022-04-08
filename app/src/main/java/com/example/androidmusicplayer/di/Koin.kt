package com.example.androidmusicplayer.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.androidmusicplayer.ImageApi
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.mediastore.MediaStoreApi
import com.example.androidmusicplayer.data.mediastore.MediaStoreDataSource
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.ui.MainViewModel
import com.example.androidmusicplayer.web.adapters.AlbumAdapter
import com.example.androidmusicplayer.web.adapters.ArtistAdapter
import com.example.androidmusicplayer.web.adapters.SongAdapter
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class Koin {
    fun start(context: Context) {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(appModule, testModule)
        }
    }

    private val appModule = module {
        single { provideApiService(androidContext()) }
        single { provideMediaStoreDataSource(get(), get()) }
        single { provideSongAdapter(androidContext()) }
        single { provideTestDatabase(androidContext()) }
        single { SongRepository(get()) }
        single {
            PreferenceDataStoreFactory.create {
                androidContext().preferencesDataStoreFile("preferences")
            }
        }
        factory { ImageApi() }
        viewModel {
            MainViewModel(get(),get())
        }
    }

    val testModule = appModule

    private fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "samsara"
        ).build()

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

    private fun provideApiService(context: Context): MediaStoreApi =
        MediaStoreApi(context)

    private fun provideSongAdapter(context: Context): SongAdapter = SongAdapter(
        ImageApi(),
        provideDatabase(context).artistDao(),
        provideDatabase(context).albumDao(),
        AlbumAdapter(),
        ArtistAdapter()
    )
}