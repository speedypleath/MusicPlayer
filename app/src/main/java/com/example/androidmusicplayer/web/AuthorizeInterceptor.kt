package com.example.androidmusicplayer.web

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor (
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("Bearer", "Bearer $token")
            .build()
    }
}