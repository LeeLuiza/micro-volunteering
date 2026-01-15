package com.example.micro_volunteering.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class UserProfileRegister : Parcelable {

    @Parcelize
    data class Volunteer(
        val name: String,
        val password: String,
        val phone: String,
        val email: String,
        val age: Int,
        val city: String
    ) : UserProfileRegister()

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
    ) : UserProfileRegister()
}