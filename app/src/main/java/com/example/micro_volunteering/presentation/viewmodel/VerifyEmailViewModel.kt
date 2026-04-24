package com.example.micro_volunteering.presentation.viewmodel

import android.os.CountDownTimer
import com.example.micro_volunteering.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.usecase.ResendCodeUseCase
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import com.example.micro_volunteering.domain.usecase.VerifyEmailUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyEmailViewModel @Inject constructor(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val resendCodeUseCase: ResendCodeUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _timerText = MutableLiveData<Int>()
    val timerText: LiveData<Int> = _timerText

    private val _timerSeconds = MutableLiveData<Long>()
    val timerSeconds: LiveData<Long> = _timerSeconds

    private val _isResendEnabled = MutableLiveData<Boolean>(false)
    val isResendEnabled: LiveData<Boolean> = _isResendEnabled

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private var timer: CountDownTimer? = null

    init {
        startTimer()
    }

    fun verifyEmail(email: String, code: String) {
        if (code.length != 6) {
            _errorText.value = resourceProvider.getString(R.string.error_input_code)
            return
        }

        _isLoading.value = true
        _isResendEnabled.value = false
        viewModelScope.launch {
            val isSuccess = verifyEmailUseCase(email, code)

            if (isSuccess) {
                createTokenUseCase()
                startTimer()
            }

            _isResendEnabled.value = true
            _isSuccess.value = isSuccess
            _isLoading.value = false
        }
    }

    fun repeatCode(email: String) {
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
                _timerText.value = R.string.resend_button
                _isResendEnabled.value = true
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    fun onErrorShown() {
        _errorText.value = null
    }
}