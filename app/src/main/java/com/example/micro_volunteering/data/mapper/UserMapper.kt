package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.request.OrganizationProfileRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterOrganizationRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterVolunteerRequest
import com.example.micro_volunteering.data.remote.dto.request.VolunteerProfileRequest
import com.example.micro_volunteering.data.remote.dto.response.OrganizationProfileResponse
import com.example.micro_volunteering.data.remote.dto.response.OrganizationUnverifiedResponse
import com.example.micro_volunteering.data.remote.dto.response.VolunteerProfileResponse
import com.example.micro_volunteering.data.remote.dto.response.VolunteerRespondResponse
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.model.VolunteerRespond

class UserMapper {

    fun toDomainUserInfo(user: VolunteerProfileResponse) = UserProfile.Volunteer(
        name = user.name,
        avatarUrl = user.avatarUrl,
        password = user.password,
        phone = user.phone,
        email = user.email,
        age = user.age,
        city = user.city,
        countTask = user.countTask,
        rating = user.rating,
        countFeedback = user.countFeedback
    )

    fun toDomainUserInfo(user: OrganizationProfileResponse) = UserProfile.Organization(
        legalName = user.legalName,
        avatarUrl = user.avatarUrl,
        inn = user.inn,
        legalAddress = user.legalAddress,
        displayName = user.displayName,
        managerPhone = user.managerPhone,
        phoneOrg = user.phoneOrg,
        email = user.email,
        city = user.city,
        isVerified = user.isVerified,
        password = user.password,
        documentUrl = user.documentUrl
    )

    fun toDtoUserProfile(user: UserProfileRegister.Volunteer) = RegisterVolunteerRequest(
        fullName = user.name,
        phone = user.phone,
        age = user.age.toString(),
        city = user.city,
        email = user.email,
        password = user.password
    )

    fun toDtoUserProfile(user: UserProfileRegister.Organization) = RegisterOrganizationRequest(
        legalName = user.legalName,
        inn = user.inn,
        legalAddress = user.legalName,
        displayName = user.displayName,
        managerPhone = user.managerPhone,
        phoneOrg = user.phoneOrg,
        email = user.email,
        city = user.city,
        password = user.password
    )

    fun toDtoUpdateProfile(user: UpdateProfileParams.Volunteer) = VolunteerProfileRequest(
        name = user.name,
        phone = user.phone,
        age = user.age,
        city = user.city,
        email = user.email
    )

    fun toDtoUpdateProfile(user: UpdateProfileParams.Organization) = OrganizationProfileRequest(
        legalName = user.legalName,
        inn = user.inn,
        legalAddress = user.legalAddress,
        displayName = user.displayName,
        managerPhone = user.managerPhone,
        phoneOrg = user.phoneOrg,
        email = user.email,
        city = user.city
    )

    fun toDomainVolunteerRespond(user: VolunteerRespondResponse) = VolunteerRespond(
        id = user.id,
        name = user.name,
        avatarUrl = user.avatarUrl,
        isRated = user.isRated
    )

   fun toDomainOrganizationUnverified(user: OrganizationUnverifiedResponse) = OrganizationUnverified(
       id = user.id,
       legalName = user.legalName,
       city = user.city,
       inn = user.inn
   )
}