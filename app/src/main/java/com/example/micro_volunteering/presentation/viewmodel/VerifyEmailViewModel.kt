package com.example.micro_volunteering.presentation.viewmodel

import android.os.CountDownTimer
import com.example.micro_volunteering.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import com.example.micro_volunteering.domain.usecase.ResendCodeUseCase
import com.example.micro_volunteering.domain.usecase.VerifyEmailUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val resendCodeUseCase: ResendCodeUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableSharedFlow<Boolean>()
    val isSuccess: SharedFlow<Boolean> = _isSuccess

    private val _timerText = MutableSharedFlow<Int>()
    val timerText: SharedFlow<Int> = _timerText

    private val _timerSeconds = MutableStateFlow<Long>(0L)
    val timerSeconds: StateFlow<Long> = _timerSeconds

    private val _isResendEnabled = MutableStateFlow<Boolean>(false)
    val isResendEnabled: StateFlow<Boolean> = _isResendEnabled

    private val _errorText = MutableSharedFlow<String?>()
    val errorText: SharedFlow<String?> = _errorText

    private var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun verifyEmail(email: String, code: String) {
        if (code.length != 6) {
            viewModelScope.launch {
                _errorText.emit(resourceProvider.getString(R.string.error_input_code))
            }
            return
        }

        _isLoading.value = true
        _isResendEnabled.value = false
        viewModelScope.launch {
            val isSuccess = verifyEmailUseCase(email, code)

            if (isSuccess) {
                createTokenUseCase()
            }

            _isResendEnabled.value = true
            _isSuccess.emit(isSuccess)
            _isLoading.value = false
        }
    }

    fun resendCode(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val isSuccess = resendCodeUseCase(email)

            if (isSuccess) {
                startTimer()
            }
            else {
                _isResendEnabled.value = true
            }

            _isLoading.value = false
        }
    }

    private fun startTimer() {
        _isResendEnabled.value = false

        timer?.cancel()
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                _timerSeconds.value = seconds
            }

            override fun onFinish() {
                viewModelScope.launch {
                    _timerText.emit(R.string.resend_button)
                }
                _isResendEnabled.value = true
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}