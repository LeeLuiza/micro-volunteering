package com.example.micro_volunteering.domain.repository

import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.domain.model.Notification
import com.example.micro_volunteering.domain.model.OrganizationUnverified
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.TaskStatus
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.model.VolunteerRespond

interface VolunteeringRepository {
    suspend fun registerVolunteer(user: UserProfileRegister.Volunteer) : Boolean
    suspend fun registerOrganization(user: UserProfileRegister.Organization) : Boolean
    suspend fun login(login: String, password: String) : UserRole?
    suspend fun verifyEmail(email: String, code: String) : Boolean
    suspend fun resendCode(email: String) : Boolean
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
    suspend fun uploadAvatar(uri: String) : Boolean
    suspend fun uploadDocument(uri: String) : Boolean
    suspend fun getNotifications() : List<Notification>
    suspend fun getTaskResponders(idTask: Int) : List<VolunteerRespond>
    suspend fun leaveFeedback(idVolunteer: Int, idTask: Int, text: String, countStars: Float) : Boolean
    suspend fun completeTask(id: Int) : Boolean
    suspend fun respondToTask(idTask: Int) : Boolean
    suspend fun acceptVolunteer(idTask: Int, idVolunteer: Int) : Boolean
    suspend fun rejectVolunteer(idTask: Int, idVolunteer: Int) : Boolean
    suspend fun updateFcmToken(token: String) : Boolean
    suspend fun createFcmToken() : Boolean
    suspend fun isNotificationPermissionRequested() : Boolean
    suspend fun setNotificationPermissionRequested()
    suspend fun getUnverifiedOrganizations() : List<OrganizationUnverified>
    suspend fun getUnverifiedOrganization(id: Int) : UserProfile.Organization?
    suspend fun verifyOrganization(id: Int) : Boolean
    suspend fun rejectOrganization(id: Int): Boolean
    fun isVerified(): Boolean
    fun isUserLogged(): Boolean
}