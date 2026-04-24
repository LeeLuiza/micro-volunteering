package com.example.micro_volunteering.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.micro_volunteering.domain.model.Task

class TaskDiffCallback(
    private val oldTaskList: List<Task>,
    private val newTaskList: List<Task>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTaskList.size

    override fun getNewListSize(): Int = newTaskList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldTaskList[oldItemPosition].id == newTaskList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val old = oldTaskList[oldItemPosition]
        val new = newTaskList[newItemPosition]
        return old.title == new.title &&
                old.description == new.description &&
                old.address == new.address &&
                old.organizationName == new.organizationName
    }
}