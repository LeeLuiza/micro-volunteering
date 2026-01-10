package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasks, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position], onItemClick)
    }

    override fun getItemCount() = tasks.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val address: TextView = itemView.findViewById(R.id.address)
        val organization: TextView = itemView.findViewById(R.id.organization)

        fun bind(item: Task, onItemClick: (Int) -> Unit) {
            title.text = item.title
            description.text = item.description
            address.text = item.address
            organization.text = item.organizationName

            itemView.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}