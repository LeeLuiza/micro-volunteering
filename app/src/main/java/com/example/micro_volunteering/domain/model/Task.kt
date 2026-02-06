package com.example.micro_volunteering.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val address: String,
    val organizationName: String,
    val date: String,
    val category: CategoryTask,
    val volunteersNeeded: Int
) : Parcelable