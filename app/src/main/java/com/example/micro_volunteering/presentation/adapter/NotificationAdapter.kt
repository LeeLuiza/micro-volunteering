package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemNotificationBinding
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.presentation.diffutil.NotificationDiffCallback
import com.example.micro_volunteering.presentation.viewholder.NotificationViewHolder

class NotificationAdapter(
    private val isOrganization: Boolean,
    private val onItemClick: (Int) -> Unit,
    private val onAcceptClick: (Int, Int) -> Unit,
    private val onDismissClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<NotificationViewHolder>() {

    private var notifications = listOf<Notification>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position], onItemClick, onAcceptClick, onDismissClick, isOrganization)
    }

    override fun getItemCount() = notifications.size

    fun updateNotification(newNotifications: List<Notification>) {
        val diffCallback = NotificationDiffCallback(notifications, newNotifications)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notifications = newNotifications
        diffResult.dispatchUpdatesTo(this)
    }
}