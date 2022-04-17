package com.example.androidmusicplayer

import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class ModuleTestCheck: KoinTest {
    @Test
    fun checkTestModules() {
        startKoin {
            printLogger(Level.DEBUG)
            androidContext(AndroidMusicPlayer())
            modules(AndroidMusicPlayer().testModule)
        }.checkModules()
    }
}