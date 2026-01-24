package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.response.NotificationResponse
import com.example.micro_volunteering.domain.model.Notification

class NotificationMapper {

    fun toDomain(notification: NotificationResponse) = Notification(
        message = notification.message,
        createdAt = notification.createdAt,
        taskName = notification.taskName,
        avatarUrl = notification.avatarUrl,
        idUser = notification.idUser,
        idTask = notification.idTask,
    )
}