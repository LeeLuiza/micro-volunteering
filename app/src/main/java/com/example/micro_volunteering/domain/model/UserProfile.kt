package com.example.micro_volunteering.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class UserProfile : Parcelable {

    @Parcelize
    data class Volunteer(
        val name: String,
        val password: String,
        val phone: String,
        val email: String,
        val age: Int,
        val city: String
    ) : UserProfile()

    @Parcelize
    data class Organization(
        val legalName: String,
        val inn: String,
        val legalAddress: String,
        val displayName: String,
        val managerPhone: String,
        val phoneOrg: String,
        val email: String,
        val city: String,
        val isVerified: Boolean,
        val password: String
    ) : UserProfile()
}