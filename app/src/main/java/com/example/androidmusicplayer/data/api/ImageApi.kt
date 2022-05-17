package com.example.androidmusicplayer.data.api

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.imageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class ImageApi(
    private val context: Context
): ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
                .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
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

    fun getBitmapFromUrl(
        fileName: String,
        options: BitmapFactory.Options = BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 }
    ): Bitmap {
        var failures = 0
        while (true) {
            try {
                var bitmap: Bitmap
                val req = ImageRequest.Builder(context)
                    .data(fileName) // demo link
                    .build()
                runBlocking { bitmap = context.imageLoader.execute(req).drawable?.toBitmap(256, 256)!! }
                return bitmap
            } catch (e: Exception) {
                if (failures++ > 5) return BitmapFactory.decodeStream(context.assets.open("placeholder-images-image_large.webp"), null, options)!!
            }
        }
    }
}