package com.example.micro_volunteering.domain.repository

import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.model.VolunteerRespond

interface VolunteeringRepository {
    suspend fun registrationVolunteer(user: UserProfileRegister.Volunteer) : Boolean
    suspend fun registrationOrganization(user: UserProfileRegister.Organization) : Boolean
    suspend fun authorizationUser(login: String, password: String) : Boolean
    suspend fun getTasks() : List<Task>
    suspend fun getTask(id: Int) : Task?
    suspend fun getTasksOrganization(status: TaskStatus) : List<Task>
    suspend fun updateTask(
        id: Int, title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int?
    suspend fun createTask(
        title: String, description: String, address: String,
        category: String, volunteersNeeded: Int
    ) : Int?
    suspend fun getVolunteerInfo() : UserProfile.Volunteer?
    suspend fun getOrganizationInfo() : UserProfile.Organization?
    suspend fun updateVolunteerInfo(userInfo: UpdateProfileParams.Volunteer) : Boolean
    suspend fun updateOrganizationInfo(userInfo: UpdateProfileParams.Organization) : Boolean
    suspend fun getFeedbacks() : List<Feedback>
    fun getCurrentUserRole(): UserRole?
    suspend fun deleteUser() : Boolean
    suspend fun deleteTask(id: Int) : Boolean
    suspend fun logout()
    suspend fun uploadPhoto(uri: String) : Boolean
    suspend fun getNotification() : List<Notification>
    suspend fun getVolunteerRespond(idTask: Int) : List<VolunteerRespond>
    suspend fun leaveFeedback(idVolunteer: Int, idTask: Int, text: String, countStars: Float) : Boolean
    suspend fun completeTask(id: Int) : Boolean
    suspend fun respond(idTask: Int) : Boolean
    suspend fun acceptVolunteer(idTask: Int, idVolunteer: Int) : Boolean
    suspend fun dismissVolunteer(idTask: Int, idVolunteer: Int) : Boolean
    suspend fun updateFcmToken(token: String) : Boolean
    suspend fun createFcmToken() : Boolean
    suspend fun isNotificationPermissionRequested() : Boolean
    suspend fun setNotificationPermissionRequested()
}