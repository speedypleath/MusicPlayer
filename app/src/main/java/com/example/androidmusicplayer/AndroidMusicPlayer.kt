package com.example.androidmusicplayer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import coil.disk.DiskCache
import coil.imageLoader
import com.example.androidmusicplayer.activity.MainActivity
import com.example.androidmusicplayer.adapter.AlbumAdapter
import com.example.androidmusicplayer.adapter.ArtistAdapter
import com.example.androidmusicplayer.adapter.SongAdapter
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.api.ImageApi
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.ui.viewmodel.*
import com.example.androidmusicplayer.util.CLIENT_ID
import com.example.androidmusicplayer.util.REDIRECT_URI
import com.example.androidmusicplayer.util.Status
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AndroidMusicPlayer : Application() {
    private val appModule = module {
        single { provideRepository(get(), get()) }
        single { provideSpotifyDataSource(get()) }
        single { provideMediaStoreDataSource(get()) }
        single { provideSongAdapter( get()) }
        single { provideArtistAdapter(androidApplication(), get()) }
        single { provideAlbumAdapter(androidApplication(), get()) }
        single { provideSpotifyApi(get()) }
        single { provideMediaStoreApi(get()) }
        single { provideTestDatabase(androidApplication()) }
        single {
            PreferenceDataStoreFactory.create {
                androidApplication().preferencesDataStoreFile("preferences")
            }
        }
        factory { ImageApi(androidApplication()) }
        viewModel {
            MainViewModel(get())
        }
        viewModel {
            SettingsViewModel(get(), get())
        }
        viewModel {
            PlaylistViewModel()
        }
        single {
            params -> PlayerViewModel(params.get())
        }
        single {
            LibraryViewModel()
        }
    }

    val testModule = module {
        appModule
    }

    private fun provideSpotifyApi(activity: ComponentActivity): SpotifyApi {
        val api = SpotifyApi()

        val launcher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                Log.d("Main activity", "Spotify login result: $activityResult")
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val token = AuthorizationClient.getResponse(activityResult.resultCode, activityResult.data).accessToken
                    Log.d("Main activity", "Spotify login token: $token")
                    api.loadEndpoints(token)
                    api.isInitialized
                }
            }

        val request = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        ).setScopes(
            arrayOf(
                "user-read-recently-played",
                "user-follow-read",
                "user-library-read",
                "playlist-read-private",
                "user-top-read"
            )
        ).build()

        val intent = AuthorizationClient.createLoginActivityIntent(activity, request)
        api.registerLauncher(launcher, intent)

        return api
    }

    private fun provideMediaStoreApi(activity: MainActivity): MediaStoreApi {
        val api = MediaStoreApi(activity.applicationContext)

        val launcher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Main activity", "Permission granted")
                    activity.activityResult.value = Status.SUCCESS
                    api.initialized = true
                } else {
                    Log.d("Main activity", "No permission")
                    activity.activityResult.value = Status.ERROR
                }
            }

        api.registerLauncher(launcher)

        return api
    }

    private fun provideRepository(mediaStoreDataSource: MediaStoreDataSource, spotifyDataSource: SpotifyDataSource): SongRepository =
        SongRepository(
            mediaStoreDataSource,
            spotifyDataSource
        )

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

    private fun provideMediaStoreDataSource(mediaStoreApi: MediaStoreApi): MediaStoreDataSource =
        MediaStoreDataSource(
            mediaStoreApi,
            Dispatchers.IO
        )

    private fun provideSongAdapter(appDatabase: AppDatabase): SongAdapter = SongAdapter(
        appDatabase.artistDao(),
        appDatabase.albumDao(),
        appDatabase.songDao()
    )

    private fun provideArtistAdapter(context: Context, appDatabase: AppDatabase): ArtistAdapter = ArtistAdapter(
        ImageApi(context),
        appDatabase.artistDao()
    )

    private fun provideAlbumAdapter(context: Context, appDatabase: AppDatabase): AlbumAdapter = AlbumAdapter(
        ImageApi(context),
        appDatabase.albumDao()
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidMusicPlayer)
            androidLogger()
            modules(appModule)
        }
        applicationContext.imageLoader
            .newBuilder()
            .memoryCache {
                coil.memory.MemoryCache.Builder(applicationContext)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(applicationContext.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .okHttpClient {
                // Don't limit concurrent network requests by host.
                val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }

                // Lazily create the OkHttpClient that is used for network operations.
                OkHttpClient.Builder()
                    .dispatcher(dispatcher)
                    .build()
            }
            .crossfade(true)
            .build()
        instance = this
    }

    companion object {
        lateinit var instance: AndroidMusicPlayer
    }
}