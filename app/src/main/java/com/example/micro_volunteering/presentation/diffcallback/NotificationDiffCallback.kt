package com.example.micro_volunteering.presentation.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.micro_volunteering.domain.model.Notification

class NotificationDiffCallback(
    private val oldList: List<Notification>,
    private val newList: List<Notification>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].idTask == newList[newItemPosition].idTask &&
                oldList[oldItemPosition].idUser == newList[newItemPosition].idUser
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.avatarUrl == new.avatarUrl &&
                old.taskName == new.taskName &&
                old.createdAt == new.createdAt &&
                old.message == new.message
    }
}