package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemOrganizationBinding
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.presentation.diffcallback.OrganizationDiffCallback

class OrganizationAdapter (
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<OrganizationAdapter.ViewHolder>() {

    private var organizations = listOf<OrganizationUnverified>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemOrganizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(organizations[position], onItemClick)
    }

    override fun getItemCount() = organizations.size

    class ViewHolder(private val binding: ItemOrganizationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrganizationUnverified, onItemClick: (Int) -> Unit) {
            binding.name.text = item.legalName
            binding.city.text = item.city
            binding.inn.text = item.inn

            binding.root.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }

    fun updateOrganizations(newOrganizations: List<OrganizationUnverified>) {
        val diffCallback = OrganizationDiffCallback(organizations, newOrganizations)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        organizations = newOrganizations
        diffResult.dispatchUpdatesTo(this)
    }
}