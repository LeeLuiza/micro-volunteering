package com.example.micro_volunteering.presentation.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.micro_volunteering.databinding.ItemVolunteerRespondBinding
import com.example.micro_volunteering.domain.model.VolunteerRespond

class VolunteerRespondViewHolder(private val binding: ItemVolunteerRespondBinding) : RecyclerView.ViewHolder(binding.root) {

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