package com.example.androidmusicplayer

import android.content.Context
import android.content.ContextWrapper
import androidx.test.core.app.ApplicationProvider
import com.example.androidmusicplayer.di.Koin
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import okio.buffer
import okio.source
import org.junit.Assume
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class CoilTest: KoinTest {
    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
        Mockito.mock(Context::class.java)
        Mockito.mock(ContextWrapper::class.java)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(MainApp())
        modules(Koin().testModule)
    }

    fun assumeTrue(actual: Boolean, message: String = "") {
        if (message.isBlank()) {
            Assume.assumeTrue(actual)
        } else {
            Assume.assumeTrue(message, actual)
        }
    }

    fun createMockWebServer(vararg images: String): MockWebServer {
        val server = MockWebServer()
        images.forEach { server.enqueueImage(it) }
        return server.apply { start() }
    }

    private fun MockWebServer.enqueueImage(image: String, headers: Headers = headersOf()): Long {
        val buffer = Buffer()
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.assets.open(image).source().buffer().readAll(buffer)
        enqueue(MockResponse().setHeaders(headers).setBody(buffer))
        return buffer.size
    }
}