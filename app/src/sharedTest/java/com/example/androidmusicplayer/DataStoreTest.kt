package com.example.androidmusicplayer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
abstract class DataStoreTest(
    coroutineTestImpl: CoroutineTest = CoroutineTestImpl()
): CoroutineTest by coroutineTestImpl, AutoCloseKoinTest() {
    private lateinit var preferencesScope: CoroutineScope
    protected lateinit var dataStore: DataStore<Preferences>

    @Before
    fun createDatastore() {
        preferencesScope = CoroutineScope(testDispatcher + Job())

        dataStore = PreferenceDataStoreFactory.create(scope = preferencesScope) {
            InstrumentationRegistry.getInstrumentation().targetContext.preferencesDataStoreFile(
                "test-preferences-file"
            )
        }
    }

    @After
    fun removeDatastore() {
        File(
            ApplicationProvider.getApplicationContext<Context>().filesDir,
            "datastore"
        ).deleteRecursively()

        preferencesScope.cancel()
    }
}