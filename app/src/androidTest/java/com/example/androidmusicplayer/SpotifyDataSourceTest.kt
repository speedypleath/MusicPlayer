package com.example.androidmusicplayer

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androidmusicplayer.data.SpotifyDataSource
import com.example.androidmusicplayer.ui.MainActivity
import com.example.androidmusicplayer.web.AuthorizationInterceptor
import com.example.androidmusicplayer.web.SpotifyApiEndpoint
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class SpotifyDataSourceTest: KoinTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    private val mockWebServer by lazy { MockWebServer() }
    private val token = "BQAOwFjU9xGqCpTSy8z8mX4qfCG5S1aPOIhiV7knBlbgk8-QgvpLL0tEzSV5o76WPom3fB5pCuz1N2vo7GDAWNHko9kRxJC0Jfq5bZh6GIu9NTI2kn3S9Huc_MHHAl-slYxt4UaR6f61Bnqc6kt2MRZohMZSsTnFQ858IJMXfVtDETHsuh7d1Y0ZfQpJAmYg13aIo4hfed9M-sO049Cw4A"
    val builder = OkHttpClient.Builder()
    lateinit var client: OkHttpClient
    lateinit var api: SpotifyApiEndpoint
    lateinit var spotifyDataSource: SpotifyDataSource
    lateinit var spotifyService: SpotifyApiEndpoint

    @Before
    fun mockAuth() {
        builder.addInterceptor(AuthorizationInterceptor(token))

        client = builder
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.spotify.com"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpotifyApiEndpoint::class.java)

        spotifyDataSource = SpotifyDataSource(api)
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun loadMediaStore() = runTest {
        val res = api.getSong("11dFghVXANMlKmJXsNCbNl")
        res.awaitResponse()
    }
}