package com.example.micro_volunteering.domain.repository

import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserRole

interface VolunteeringRepository {
    suspend fun registrationVolunteer(user: UserProfile.Volunteer) : Boolean
    suspend fun registrationOrganization(user: UserProfile.Organization) : Boolean
    suspend fun authorizationUser(login: String, password: String) : Boolean
    suspend fun getTasks() : List<Task>
    suspend fun getTask(id: Int) : Task?
    suspend fun getTasksOrganization() : List<Task>
    suspend fun updateTask(
        id: Int, title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int?
    suspend fun createTask(
        title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int?
    suspend fun getVolunteerInfo() : UserProfile?
    suspend fun getOrganizationInfo() : UserProfile?
    suspend fun updateVolunteerInfo(userInfo: UpdateProfileParams.Volunteer) : Boolean
    suspend fun updateOrganizationInfo(userInfo: UpdateProfileParams.Organization) : Boolean
    fun getCurrentUserRole(): UserRole?
    suspend fun deleteUser() : Boolean
    suspend fun logout()
}