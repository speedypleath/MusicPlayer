package com.example.androidmusicplayer.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.spotify.sdk.android.auth.AuthorizationClient

import com.spotify.sdk.android.auth.AuthorizationResponse

import com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE




class LoginActivity: Activity() {
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, intent)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> Log.d("Login Activity", "Success ${AuthorizationResponse.Type.TOKEN}")
                AuthorizationResponse.Type.ERROR -> Log.d("Login Activity", "Error ${AuthorizationResponse.Type.ERROR}")
                else -> {}
            }
        }
    }
}
