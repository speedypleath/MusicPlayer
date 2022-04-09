package com.example.androidmusicplayer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.After
import org.junit.Before

interface CoroutineTest {
    var testDispatcher: CoroutineDispatcher
    var testCoroutineScope: TestScope

    @Before
    fun setupViewModelScope()

    @After
    fun cleanupViewModelScope()

    fun coroutineTest(block: suspend TestScope.() -> Unit)
}