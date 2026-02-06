package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemTasksBinding
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.presentation.diffcallback.TaskDiffCallback

class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTasksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position], onItemClick)
    }

    override fun getItemCount() = tasks.size

    class ViewHolder(private val binding: ItemTasksBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task, onItemClick: (Int) -> Unit) {
            binding.title.text = item.title
            binding.description.text = item.description
            binding.address.text = item.address
            binding.organization.text = item.organizationName

            binding.root.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }

    fun updateTasks(newTasks: List<Task>) {
        val diffCallback = TaskDiffCallback(tasks, newTasks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasks = newTasks
        diffResult.dispatchUpdatesTo(this)
    }
}