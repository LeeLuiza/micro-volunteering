package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemFeedbackBinding
import com.example.micro_volunteering.domain.model.Feedback

class FeedbackAdapter (
    private var feedbacks: List<Feedback>
) : RecyclerView.Adapter<FeedbackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedbacks[position])
    }

    override fun getItemCount() = feedbacks.size

    class ViewHolder(private val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedback: Feedback) {
            binding.name.text = feedback.nameUser
            binding.ratingBar.rating = feedback.countStars
            binding.comment.text = feedback.text
        }
    }

    fun updateFeedbacks(newFeedbacks: List<Feedback>) {
        feedbacks = newFeedbacks
        notifyDataSetChanged()
    }
}