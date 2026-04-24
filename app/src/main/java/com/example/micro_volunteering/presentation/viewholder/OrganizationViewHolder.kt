package com.example.micro_volunteering.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemOrganizationBinding
import com.example.micro_volunteering.domain.model.OrganizationUnverified

class OrganizationViewHolder(private val binding: ItemOrganizationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: OrganizationUnverified, onItemClick: (Int) -> Unit) {
        binding.name.text = item.legalName
        binding.city.text = item.city
        binding.inn.text = item.inn

        binding.root.setOnClickListener {
            onItemClick(item.id)
        }
    }
}