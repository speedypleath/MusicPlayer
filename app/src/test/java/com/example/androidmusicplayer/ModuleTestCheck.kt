package com.example.androidmusicplayer
import android.content.Context
import com.example.androidmusicplayer.di.Koin
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

@Category(CheckModuleTest::class)
class ModuleTestCheck: KoinTest {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
        Mockito.mock(Context::class.java)
    }

    @Test
    fun checkTestModules() {
        startKoin {
            printLogger(Level.DEBUG)
            androidContext(MainApp())
            modules(Koin().testModule)
        }.checkModules()
    }
}