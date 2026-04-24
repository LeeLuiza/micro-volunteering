package com.example.micro_volunteering.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.micro_volunteering.domain.model.Feedback

class FeedbackDiffCallback(
    private val oldList: List<Feedback>,
    private val newList: List<Feedback>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.nameUser == new.nameUser && old.text == new.text && old.countStars == new.countStars
    }
}