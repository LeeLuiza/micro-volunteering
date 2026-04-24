package com.example.micro_volunteering.presentation.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.micro_volunteering.databinding.ItemNotificationBinding
import com.example.micro_volunteering.domain.model.Notification

class NotificationViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        notification: Notification, onItemClick: (Int) -> Unit, onAcceptClick: (Int, Int) -> Unit,
        onDismissClick: (Int, Int) -> Unit, isOrganization: Boolean
    ) {
        binding.root.setOnClickListener {
            onItemClick(notification.idUser)
        }

        binding.btnDismiss.setOnClickListener {
            onDismissClick(notification.idTask, notification.idUser)
        }

        binding.btnAccept.setOnClickListener {
            onAcceptClick(notification.idTask, notification.idUser)
        }

        binding.message.text = notification.message
        binding.taskName.text = notification.taskName
        binding.createAt.text = notification.createdAt
        binding.img.load(notification.avatarUrl)

        if (isOrganization) {
            binding.btnAccept.isVisible = true
            binding.btnDismiss.isVisible = true
        }
        else {
            binding.btnAccept.isVisible = false
            binding.btnDismiss.isVisible = false
        }
    }
}