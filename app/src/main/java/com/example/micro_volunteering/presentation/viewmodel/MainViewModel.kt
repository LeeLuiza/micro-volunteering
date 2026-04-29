package com.example.micro_volunteering.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Int?>()
    val navigationEvent: SharedFlow<Int?> = _navigationEvent

    fun handleNotificationIntent(intent: Intent?) {
        val targetScreen = intent?.extras?.getString("screen")

        if (targetScreen == "notifications") {
            viewModelScope.launch {
                _navigationEvent.emit(R.id.notificationFragment)
            }
        }
    }
}