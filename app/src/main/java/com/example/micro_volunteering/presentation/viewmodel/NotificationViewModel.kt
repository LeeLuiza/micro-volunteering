package com.example.micro_volunteering.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.usecase.AcceptVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.GetNotificationsUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.RejectVolunteerUseCase
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    private val _toastMessage = MutableSharedFlow<String?>()
    val toastMessage: SharedFlow<String?> = _toastMessage

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
                filterNotifications(idTask, idVolunteer)
                _toastMessage.emit(resourceProvider.getString(R.string.accepted_user))
            }
            _isLoading.value = false
        }
    }

    fun dismissVolunteer(idTask: Int, idVolunteer: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            val isSuccess = rejectVolunteerUseCase(idTask, idVolunteer)
            if (isSuccess) {
                filterNotifications(idTask, idVolunteer)
                _toastMessage.emit(resourceProvider.getString(R.string.dismiss_user))
            }
            _isLoading.value = false
        }
    }

    fun isUserOrganization(): Boolean {
        return getUserRoleUseCase() == UserRole.ORGANIZATION
    }

    private fun filterNotifications(idTask: Int, idVolunteer: Int) {
        val currentList = _notifications.value
        val newNotification = currentList.filter { notification ->
            !(notification.idTask == idTask && notification.idUser == idVolunteer)
        }
        _notifications.value = newNotification
    }
}