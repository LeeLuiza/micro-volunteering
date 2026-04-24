package com.example.micro_volunteering.presentation.utils

import android.content.Context
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.model.CategoryTask

fun CategoryTask.getDisplayName(context: Context) : String {
    val resId = when (this) {
        CategoryTask.ECOLOGY -> R.string.ecology
        CategoryTask.ANIMAL -> R.string.animal
        CategoryTask.SOCIAL_ASSIST -> R.string.social_assist
        CategoryTask.CAR -> R.string.car
        CategoryTask.MENTAL -> R.string.mental
        CategoryTask.EVENT -> R.string.event
        CategoryTask.OTHER -> R.string.other
    }
    return context.getString(resId)
}