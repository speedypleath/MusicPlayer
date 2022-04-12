package com.example.androidmusicplayer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.androidmusicplayer.adapters.AlbumAdapter
import com.example.androidmusicplayer.adapters.ArtistAdapter
import com.example.androidmusicplayer.adapters.SongAdapter
import com.example.androidmusicplayer.data.AppDatabase
import com.example.androidmusicplayer.data.api.MediaStoreApi
import com.example.androidmusicplayer.data.api.SpotifyApi
import com.example.androidmusicplayer.data.repository.SongRepository
import com.example.androidmusicplayer.data.truth.MediaStoreDataSource
import com.example.androidmusicplayer.data.truth.SpotifyDataSource
import com.example.androidmusicplayer.ui.MainScreen
import com.example.androidmusicplayer.ui.MainViewModel
import com.example.androidmusicplayer.ui.theme.AndroidMusicPlayerTheme
import com.example.androidmusicplayer.util.CLIENT_ID
import com.example.androidmusicplayer.util.REDIRECT_URI
import com.example.androidmusicplayer.util.Status
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
private val appModule = module {
    single { provideRepository(get(), get()) }
    single { provideSpotifyDataSource(get()) }
    single { provideMediaStoreDataSource(get()) }
    single { provideSongAdapter( get()) }
    single { provideArtistAdapter(androidContext(), get()) }
    single { provideAlbumAdapter(androidContext(), get()) }
    single { provideSpotifyApi(get()) }
    single { provideMediaStoreApi(get()) }
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile("preferences")
        }
    }
    factory { ImageApi(androidContext()) }
    viewModel {
        MainViewModel(get())
    }
}

val testModule = module {
    appModule
    single { provideTestDatabase(androidContext()) }
}
private fun provideSpotifyApi(activity: ComponentActivity): SpotifyApi {
    val api = SpotifyApi()

    val launcher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d("Main activity", "Spotify login result: $activityResult")
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val token = AuthorizationClient.getResponse(activityResult.resultCode, activityResult.data).accessToken
                Log.d("Main activity", "Spotify login token: $token")
                api.initialized = true
                api.loadEndpoints(token)
                activity.setContent {
                    AndroidMusicPlayerTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            MainScreen()
                        }
                    }
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

private fun provideMediaStoreApi(activity: ComponentActivity): MediaStoreApi {
    val api = MediaStoreApi(activity.applicationContext)

    val launcher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("Main activity", "Permission granted")
                api.initialized = true
                MutableLiveData(Status.SUCCESS)
            } else {
                Log.d("Main activity", "No permission")
                MutableLiveData(Status.ERROR)
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