package com.example.micro_volunteering.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.micro_volunteering.data.constants.AppConstants
import javax.inject.Inject

class TokenPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit {
            putString(AppConstants.KEY_ACCESS_TOKEN, accessToken)
                .putString(AppConstants.KEY_REFRESH_TOKEN, refreshToken)
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(AppConstants.KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(AppConstants.KEY_REFRESH_TOKEN, null)
    }

    fun deleteToken() {
        sharedPreferences.edit {
            remove(AppConstants.KEY_ACCESS_TOKEN)
            remove(AppConstants.KEY_REFRESH_TOKEN)
        }
    }
}