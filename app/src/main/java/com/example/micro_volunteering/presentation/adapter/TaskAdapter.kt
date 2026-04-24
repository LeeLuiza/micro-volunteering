package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemTasksBinding
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.presentation.diffutil.TaskDiffCallback
import com.example.micro_volunteering.presentation.viewholder.TaskViewHolder

class TaskAdapter(
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

    private var tasks = listOf<Task>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position], onItemClick)
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        val diffCallback = TaskDiffCallback(tasks, newTasks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }
}