package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemOrganizationBinding
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.presentation.diffutil.OrganizationDiffCallback
import com.example.micro_volunteering.presentation.viewholder.OrganizationViewHolder

class OrganizationAdapter (
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<OrganizationViewHolder>() {

    private var organizations = listOf<OrganizationUnverified>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrganizationViewHolder {
        val binding = ItemOrganizationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrganizationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrganizationViewHolder,
        position: Int
    ) {
        holder.bind(organizations[position], onItemClick)
    }

    override fun getItemCount() = organizations.size

    fun updateOrganizations(newOrganizations: List<OrganizationUnverified>) {
        val diffCallback = OrganizationDiffCallback(organizations, newOrganizations)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        organizations = newOrganizations
        diffResult.dispatchUpdatesTo(this)
    }
}