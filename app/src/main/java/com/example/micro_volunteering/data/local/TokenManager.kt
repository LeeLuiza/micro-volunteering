package com.example.micro_volunteering.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.micro_volunteering.data.constants.AuthConstants
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(AuthConstants.KEY_ACCESS_TOKEN, accessToken)
                .putString(AuthConstants.KEY_REFRESH_TOKEN, refreshToken)
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(AuthConstants.KEY_ACCESS_TOKEN, null)
    }
}