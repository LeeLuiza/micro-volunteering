package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemVolunteerRespondBinding
import com.example.micro_volunteering.domain.model.VolunteerRespond
import com.example.micro_volunteering.presentation.diffutil.VolunteerRespondDiffCallback
import com.example.micro_volunteering.presentation.viewholder.VolunteerRespondViewHolder

class RespondersAdapter(
    private val onItemClick: (Int, Float, String, String?) -> Unit
) : RecyclerView.Adapter<VolunteerRespondViewHolder>() {

    private var volunteerResponds = listOf<VolunteerRespond>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VolunteerRespondViewHolder {
        val binding = ItemVolunteerRespondBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VolunteerRespondViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VolunteerRespondViewHolder,
        position: Int
    ) {
        holder.bind(volunteerResponds[position], onItemClick)
    }

    override fun getItemCount() = volunteerResponds.size

    fun update(newVolunteerResponds: List<VolunteerRespond>) {
        val diffCallback = VolunteerRespondDiffCallback(volunteerResponds, newVolunteerResponds)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        volunteerResponds = newVolunteerResponds
        diffResult.dispatchUpdatesTo(this)
    }
}