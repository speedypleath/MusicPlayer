package com.example.androidmusicplayer.web

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor (
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer $token")

        return chain.proceed(requestBuilder.build())
    }
}