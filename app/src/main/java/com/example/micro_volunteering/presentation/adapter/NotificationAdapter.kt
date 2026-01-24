package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.micro_volunteering.databinding.ItemNotificationBinding
import com.example.micro_volunteering.domain.model.Notification

class NotificationAdapter(
    private var notifications: List<Notification>,
    private val isOrganization: Boolean,
    private val onItemClick: (Int) -> Unit,
    private val onAcceptClick: (Int, Int) -> Unit,
    private val onDismissClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notifications[position], onItemClick, onAcceptClick, onDismissClick, isOrganization)
    }

    override fun getItemCount() = notifications.size

    class ViewHolder(private val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

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

    fun updateNotification(newNotifications: List<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }
}