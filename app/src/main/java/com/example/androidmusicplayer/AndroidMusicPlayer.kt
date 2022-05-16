package com.example.androidmusicplayer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import coil.disk.DiskCache
import coil.imageLoader
import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.ui.App
import com.example.androidmusicplayer.ui.viewmodel.MainViewModel
import com.example.androidmusicplayer.ui.viewmodel.SettingsViewModel
import com.example.androidmusicplayer.util.CLIENT_ID
import com.example.androidmusicplayer.util.REDIRECT_URI
import com.example.androidmusicplayer.util.Status
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
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
        single { provideArtistAdapter(androidContext(), get()) }
        single { provideAlbumAdapter(androidContext(), get()) }
        single { provideSpotifyApi(get()) }
        single { provideMediaStoreApi(get()) }
        single { provideTestDatabase(androidContext()) }
        single {
            PreferenceDataStoreFactory.create {
                androidContext().preferencesDataStoreFile("preferences")
            }
        }
        factory { ImageApi(androidContext()) }
        viewModel {
            MainViewModel(get())
        }
        viewModel {
            SettingsViewModel(get(), get())
        }
    }

    val testModule = module {
        appModule
    }
    fun provideSpotifyApi(activity: ComponentActivity): SpotifyApi {
        val api = SpotifyApi()

        val launcher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                Log.d("Main activity", "Spotify login result: $activityResult")
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val token = AuthorizationClient.getResponse(activityResult.resultCode, activityResult.data).accessToken
                    Log.d("Main activity", "Spotify login token: $token")
                    api.loadEndpoints(token)
                    activity.setContent {
                        App()
                    }
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

    fun provideMediaStoreApi(activity: MainActivity): MediaStoreApi {
        val api = MediaStoreApi(activity.applicationContext)

        val launcher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Main activity", "Permission granted")
                    activity.activityResult.value = Status.SUCCESS
                } else {
                    Log.d("Main activity", "No permission")
                    activity.activityResult.value = Status.ERROR
                }
            }

        api.registerLauncher(launcher)

        return api
    }

    fun provideRepository(mediaStoreDataSource: MediaStoreDataSource, spotifyDataSource: SpotifyDataSource): SongRepository =
        SongRepository(
            mediaStoreDataSource,
            spotifyDataSource
        )

    fun provideSpotifyDataSource(spotifyApi: SpotifyApi): SpotifyDataSource =
        SpotifyDataSource(
            spotifyApi,
            Dispatchers.IO,
        )

    fun provideTestDatabase(context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    fun provideMediaStoreDataSource(mediaStoreApi: MediaStoreApi): MediaStoreDataSource =
        MediaStoreDataSource(
            mediaStoreApi,
            Dispatchers.IO
        )

    fun provideSongAdapter(appDatabase: AppDatabase): SongAdapter = SongAdapter(
        appDatabase.artistDao(),
        appDatabase.albumDao(),
        appDatabase.songDao()
    )

    fun provideArtistAdapter(context: Context, appDatabase: AppDatabase): ArtistAdapter = ArtistAdapter(
        ImageApi(context),
        appDatabase.artistDao()
    )

    fun provideAlbumAdapter(context: Context, appDatabase: AppDatabase): AlbumAdapter = AlbumAdapter(
        ImageApi(context),
        appDatabase.albumDao()
    )

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidMusicPlayer)
            androidLogger()
            modules(appModule, testModule)
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