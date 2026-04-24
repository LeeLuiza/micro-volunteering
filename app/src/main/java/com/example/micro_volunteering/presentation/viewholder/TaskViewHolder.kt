package com.example.micro_volunteering.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemTasksBinding
import com.example.micro_volunteering.domain.model.Task

class TaskViewHolder(private val binding: ItemTasksBinding) : RecyclerView.ViewHolder(binding.root) {

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