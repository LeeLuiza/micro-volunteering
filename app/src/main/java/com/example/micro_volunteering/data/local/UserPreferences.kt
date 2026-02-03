package com.example.micro_volunteering.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import com.example.micro_volunteering.data.constants.AppConstants
import androidx.core.content.edit
import com.example.micro_volunteering.domain.model.UserRole

class UserPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUserId(userId: String) {
        sharedPreferences.edit {
            putString(AppConstants.KEY_USER_ID, userId)
        }
    }

    fun saveUserRole(role: UserRole) {
        sharedPreferences.edit {
            putString(AppConstants.USER_ROLE, role.name)
        }
    }

    fun getUserRole(): UserRole? {
        val roleString = sharedPreferences.getString(AppConstants.USER_ROLE, null) ?: return null
        return try {
            UserRole.valueOf(roleString)
        } catch (e: Exception) {
            null
        }
    }

    fun deleteUserIdAndRole() {
        sharedPreferences.edit {
            remove(AppConstants.KEY_USER_ID)
            remove(AppConstants.USER_ROLE)
        }
    }

    fun isNotificationPermissionRequested(): Boolean {
        return sharedPreferences.getBoolean(AppConstants.KEY_NOTIFICATION_REQUESTED, false)
    }

    fun setNotificationPermissionRequested() {
        sharedPreferences.edit {
            putBoolean(AppConstants.KEY_NOTIFICATION_REQUESTED, true)
        }
    }
}