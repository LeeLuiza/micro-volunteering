package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.AcceptVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.RejectVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.GetNotificationsUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getUserRoleUseCase: GetUserRoleUseCase,
    private val rejectVolunteerUseCase: RejectVolunteerUseCase,
    private val acceptVolunteerUseCase: AcceptVolunteerUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        _isLoading.value = true

        viewModelScope.launch {
            _notifications.value = getNotificationsUseCase()
            _isLoading.value = false
        }
    }

    fun acceptVolunteer(idTask: Int, idVolunteer: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = acceptVolunteerUseCase(idTask, idVolunteer)

            if (isSuccess) {
                val currentList = _notifications.value ?: emptyList()
                val newNotification = currentList.filter { notification ->
                    !(notification.idTask == idTask && notification.idUser == idVolunteer)
                }
                _notifications.value = newNotification

                _toastMessage.value = resourceProvider.getString(R.string.accepted_user)
            }

            _isLoading.value = false
        }
    }

    fun dismissVolunteer(idTask: Int, idVolunteer: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = rejectVolunteerUseCase(idTask, idVolunteer)

            if (isSuccess) {
                val currentList = _notifications.value ?: emptyList()
                val newNotification = currentList.filter { notification ->
                    !(notification.idTask == idTask && notification.idUser == idVolunteer)
                }
                _notifications.value = newNotification

                _toastMessage.value = resourceProvider.getString(R.string.dismiss_user)
            }

            _isLoading.value = false
        }
    }

    fun isUserOrganization(): Boolean {
        return getUserRoleUseCase() == UserRole.ORGANIZATION
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}