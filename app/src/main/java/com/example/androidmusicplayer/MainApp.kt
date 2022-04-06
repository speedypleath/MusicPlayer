package com.example.androidmusicplayer

import android.app.Application
import android.content.Context
import com.example.androidmusicplayer.data.mediastore.MediaStoreApi
import com.example.androidmusicplayer.data.mediastore.MediaStoreDataSource
import com.example.androidmusicplayer.data.song.SongRepository
import com.example.androidmusicplayer.ui.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val testModule = module {
        single { provideApiService(androidContext()) }
        single { provideMediaStoreDataSource(get())}
        single { SongRepository(get()) }
        viewModel {
            MainViewModel(get(),get())
        }
}

private fun provideMediaStoreDataSource(mediaStoreApi: MediaStoreApi): MediaStoreDataSource =
    MediaStoreDataSource(mediaStoreApi, Dispatchers.IO)

private fun provideApiService(context: Context): MediaStoreApi = MediaStoreApi(context)

class MainApp : Application() {
    private val appModule = module {
        single { provideApiService(androidContext()) }
        single { provideMediaStoreDataSource(get())}
        single { SongRepository(get()) }
        viewModel {
            MainViewModel(get(),get())
        }
    }

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