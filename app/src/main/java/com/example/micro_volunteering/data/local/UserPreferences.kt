package com.example.micro_volunteering.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import com.example.micro_volunteering.data.constants.AuthConstants
import androidx.core.content.edit
import com.example.micro_volunteering.domain.model.UserRole

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString(AuthConstants.KEY_USER_ID, userId)
        }
    }

    fun saveUserRole(role: UserRole) {
        sharedPreferences.edit {
            putString(AuthConstants.USER_ROLE, role.name)
        }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(AuthConstants.KEY_USER_ID, null)
    }

    fun getUserRole(): UserRole? {
        val roleString = sharedPreferences.getString(AuthConstants.USER_ROLE, null) ?: return null
        return try {
            UserRole.valueOf(roleString)
        } catch (e: Exception) {
            null
        }
    }

    fun deleteUserIdAndRole() {
        sharedPreferences.edit {
            remove(AuthConstants.KEY_USER_ID)
            remove(AuthConstants.USER_ROLE)
        }
    }
}