package com.example.micro_volunteering.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.micro_volunteering.databinding.ItemFeedbackBinding
import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.presentation.diffutil.FeedbackDiffCallback
import com.example.micro_volunteering.presentation.viewholder.FeedbackViewHolder

class FeedbackAdapter () : RecyclerView.Adapter<FeedbackViewHolder>() {

    private var feedbacks = listOf<Feedback>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedbackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbacks[position])
    }

    override fun getItemCount() = feedbacks.size

    fun updateFeedbacks(newFeedbacks: List<Feedback>) {
        val diffCallback = FeedbackDiffCallback(feedbacks, newFeedbacks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        feedbacks = newFeedbacks
        diffResult.dispatchUpdatesTo(this)
    }
}