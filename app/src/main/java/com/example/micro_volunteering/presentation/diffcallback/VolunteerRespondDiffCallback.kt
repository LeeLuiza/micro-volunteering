package com.example.micro_volunteering.presentation.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.micro_volunteering.domain.model.VolunteerRespond

class VolunteerRespondDiffCallback(
    private val oldList: List<VolunteerRespond>,
    private val newList: List<VolunteerRespond>
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
        return old.name == new.name &&
                old.avatarUrl == new.avatarUrl &&
                old.isRated == new.isRated
    }
}