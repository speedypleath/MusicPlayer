package com.example.androidmusicplayer

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import com.example.androidmusicplayer.web.AuthorizationInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class ImageApi: Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
                .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(this.cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .okHttpClient {
                    // Don't limit concurrent network requests by host.
                    val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }

                    // Lazily create the OkHttpClient that is used for network operations.
                    OkHttpClient.Builder()
                        .dispatcher(dispatcher)
                        .build()
                }
                .crossfade(true)
                .respectCacheHeaders(false)
                .build()
    }

    fun setAuthorization(token: String) {
        imageLoader.newBuilder().okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(token))
                .build()
        }.build()
    }

    fun getBitmapFromUrl(
        fileName: String,
        options: BitmapFactory.Options = BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 }
    ): Bitmap {
        // Retry multiple times as the emulator can be flaky.
        var failures = 0
        while (true) {
            try {
                return BitmapFactory.decodeStream(assets.open(fileName), null, options)!!
            } catch (e: Exception) {
                if (failures++ > 5) throw e
            }
        }
    }

    fun copyAssetToFile(fileName: String): File {
        val source = assets.open(fileName).source()
        val file = File(filesDir.absolutePath + File.separator + fileName)
        val sink = file.sink().buffer()
        source.use { sink.use { sink.writeAll(source) } }
        return file
    }
}