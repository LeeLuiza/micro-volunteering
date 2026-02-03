package com.example.micro_volunteering.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.micro_volunteering.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _navigationEvent = MutableLiveData<Int?>()
    val navigationEvent: LiveData<Int?> = _navigationEvent

    fun handleNotificationIntent(intent: Intent?) {
        val targetScreen = intent?.extras?.getString("screen")

        if (targetScreen == "notifications") {
            _navigationEvent.value = R.id.notificationFragment
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}