package com.example.micro_volunteering.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import com.example.micro_volunteering.data.constants.AuthConstants
import androidx.core.content.edit

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString(AuthConstants.KEY_USER_ID, userId)
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(AuthConstants.KEY_USER_ID, null)
    }

    fun deleteUserId() {
        sharedPreferences.edit {
            remove(AuthConstants.KEY_USER_ID)
        }
    }
}