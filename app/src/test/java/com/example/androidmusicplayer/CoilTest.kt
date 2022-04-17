package com.example.androidmusicplayer

//import android.content.Context
//import android.content.ContextWrapper
//import androidx.test.core.app.ApplicationProvider
//import com.example.androidmusicplayer.AndroidMusicPlayer
//import com.example.androidmusicplayer.testModule
//import okhttp3.Headers
//import okhttp3.Headers.Companion.headersOf
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import okio.Buffer
//import okio.buffer
//import okio.source
//import org.junit.Assume
//import org.junit.jupiter.api.extension.RegisterExtension
//import org.koin.test.KoinTest
//import org.koin.test.junit5.KoinTestExtension
//import org.koin.test.junit5.mock.MockProviderExtension
//import org.mockito.Mockito
//
//class CoilTest: KoinTest {
//    @JvmField
//    @RegisterExtension
//    val koinTestExtension = KoinTestExtension.create {
//        modules(testModule)
//    }
//
//    @JvmField
//    @RegisterExtension
//    val mockProvider = MockProviderExtension.create { clazz ->
//        Mockito.mock(clazz.java)
//        Mockito.mock(Context::class.java)
//        Mockito.mock(ContextWrapper::class.java)
//    }
//
//    val app: AndroidMusicPlayer = ApplicationProvider.getApplicationContext()
//
//    fun assumeTrue(actual: Boolean, message: String = "") {
//        if (message.isBlank()) {
//            Assume.assumeTrue(actual)
//        } else {
//            Assume.assumeTrue(message, actual)
//        }
//    }
//
//    fun createMockWebServer(vararg images: String): MockWebServer {
//        val server = MockWebServer()
//        images.forEach { server.enqueueImage(it) }
//        return server.apply { start() }
//    }
//
//    private fun MockWebServer.enqueueImage(image: String, headers: Headers = headersOf()): Long {
//        val buffer = Buffer()
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        context.assets.open(image).source().buffer().readAll(buffer)
//        enqueue(MockResponse().setHeaders(headers).setBody(buffer))
//        return buffer.size
//    }
//}