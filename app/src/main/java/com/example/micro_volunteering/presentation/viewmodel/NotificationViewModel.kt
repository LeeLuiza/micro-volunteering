package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.AcceptVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.DismissVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.GetNotificationUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val useCase: GetNotificationUseCase,
    private val userRoleUseCase: GetUserRoleUseCase,
    private val dismissVolunteerUseCase: DismissVolunteerUseCase,
    private val acceptVolunteerUseCase: AcceptVolunteerUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _notification = MutableLiveData<List<Notification>>()
    val notification: LiveData<List<Notification>> = _notification

    private val _isAccept = MutableLiveData<Boolean>(false)
    val isAccept: LiveData<Boolean> = _isAccept

    private val _isDismiss = MutableLiveData<Boolean>(false)
    val isDismiss: LiveData<Boolean> = _isDismiss

    fun loadNotification() {
        _isLoading.value = true

        viewModelScope.launch {
            _notification.value = useCase.getNotification()
            _isLoading.value = false
        }
    }

    fun acceptVolunteer(idTask: Int, idVolunteer: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = acceptVolunteerUseCase.acceptVolunteer(idTask, idVolunteer)

            if (isSuccess) {
                val currentList = _notification.value ?: emptyList()
                val newNotification = currentList.filter { notification ->
                    !(notification.idTask == idTask && notification.idUser == idVolunteer)
                }
                _notification.value = newNotification

                _isAccept.value = true
            }

            _isLoading.value = false
        }
    }

    fun dismissVolunteer(idTask: Int, idVolunteer: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = dismissVolunteerUseCase.dismissVolunteer(idTask, idVolunteer)

            if (isSuccess) {
                val currentList = _notification.value ?: emptyList()
                val newNotification = currentList.filter { notification ->
                    !(notification.idTask == idTask && notification.idUser == idVolunteer)
                }
                _notification.value = newNotification

                _isDismiss.value = true
            }

            _isLoading.value = false
        }
    }

    fun isUserOrganization(): Boolean {
        return userRoleUseCase.getUserRole() == UserRole.ORGANIZATION
    }

    fun onAcceptMessageShown() {
        _isAccept.value = false
    }

    fun onDismissMessageShown() {
        _isDismiss.value = false
    }
}