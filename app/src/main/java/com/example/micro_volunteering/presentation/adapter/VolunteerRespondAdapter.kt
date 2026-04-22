package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.micro_volunteering.databinding.ItemVolunteerRespondBinding
import com.example.micro_volunteering.domain.model.VolunteerRespond
import com.example.micro_volunteering.presentation.diffcallback.VolunteerRespondDiffCallback

class VolunteerRespondAdapter(
    private val onItemClick: (Int, Float, String, String?) -> Unit
) : RecyclerView.Adapter<VolunteerRespondAdapter.ViewHolder>() {

    private var volunteerResponds = listOf<VolunteerRespond>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemVolunteerRespondBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(volunteerResponds[position], onItemClick)
    }

    override fun getItemCount() = volunteerResponds.size

    class ViewHolder(private val binding: ItemVolunteerRespondBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(volunteerResponds: VolunteerRespond, onItemClick: (Int, Float, String, String?) -> Unit) {
            binding.name.text = volunteerResponds.name
            binding.img.load(volunteerResponds.avatarUrl)
            binding.ratingBar.isVisible = !volunteerResponds.isRated

            binding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    onItemClick(volunteerResponds.id, rating, volunteerResponds.name, volunteerResponds.avatarUrl)
                }
            }
        }
    }

    fun update(newVolunteerResponds: List<VolunteerRespond>) {
        val diffCallback = VolunteerRespondDiffCallback(volunteerResponds, newVolunteerResponds)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        volunteerResponds = newVolunteerResponds
        diffResult.dispatchUpdatesTo(this)
    }
}