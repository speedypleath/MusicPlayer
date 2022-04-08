package com.example.androidmusicplayer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before

class CoroutineTestImpl: CoroutineTest {
    override var testDispatcher: CoroutineDispatcher =
        StandardTestDispatcher()

    override var testCoroutineScope =
        TestScope(testDispatcher + Job())

    @Before
    override fun setupViewModelScope() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    override fun cleanupViewModelScope() {
        Dispatchers.resetMain()
    }

    override fun coroutineTest(block: suspend TestScope.() -> Unit) =
        testCoroutineScope.runTest(testBody = block)
}