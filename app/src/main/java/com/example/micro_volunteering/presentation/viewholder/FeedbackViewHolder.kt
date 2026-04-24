package com.example.micro_volunteering.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemFeedbackBinding
import com.example.micro_volunteering.domain.model.Feedback

class FeedbackViewHolder(private val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(feedback: Feedback) {
        binding.name.text = feedback.nameUser
        binding.ratingBar.rating = feedback.countStars
        binding.comment.text = feedback.text
    }
}