package com.example.androidmusicplayer
import com.example.androidmusicplayer.data.mediastore.MediaStoreApi
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito

@Category(CheckModuleTest::class)
class ModuleTestCheck: KoinTest {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun checkTestModules() {
        val mediaStoreMock = declareMock<MediaStoreApi>()
        checkModules {
            modules(testModule)
        }
    }
}