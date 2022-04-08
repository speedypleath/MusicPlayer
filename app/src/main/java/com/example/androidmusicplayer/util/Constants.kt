package com.example.androidmusicplayer.util

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.androidmusicplayer.BuildConfig

const val CLIENT_ID = BuildConfig.SPOTIFY_CLIENT_ID
const val REDIRECT_URI = BuildConfig.SPOTIFY_REDIRECT_URI
const val REQUEST_CODE = 1337
const val PLACEHOLDER_IMAGE = "https://i.picsum.photos/id/873/200/200.jpg?hmac=5NCxS9ue78kUMON6P-hozaU4JQF_xfkczFyh6sfHLKQ"
val SPOTIFY_TOKEN = stringPreferencesKey("spotify_token")
const val TEST_DATASTORE_NAME = "test_datastore"
const val STORE_TOKEN_WORK_NAME = "Storing spotify auth token inside Datastore..."