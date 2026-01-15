package com.example.micro_volunteering.data.repository

import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.local.UserPreferences
import com.example.micro_volunteering.data.mapper.FeedbackMapper
import com.example.micro_volunteering.data.mapper.TaskMapper
import com.example.micro_volunteering.data.mapper.UserMapper
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.remote.dto.request.CreateTaskRequest
import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.TaskRequest
import com.example.micro_volunteering.domain.model.Feedback
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserProfileRegister
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class VolunteeringRepositoryImpl @Inject constructor(
    private val api: VolunteeringApiService,
    private val tokenManager: TokenManager,
    private val userPreferences: UserPreferences,
    private val userMapper: UserMapper,
    private val taskMapper: TaskMapper,
    private val feedbackMapper: FeedbackMapper
) : VolunteeringRepository {
    override suspend fun registrationVolunteer(user: UserProfileRegister.Volunteer) : Boolean {
        return try {
            val response = api.registrationVolunteer(userMapper.toDtoUserProfile(user))
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            userPreferences.saveUserId(response.user.id.toString())
            userPreferences.saveUserRole(response.user.role)
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun registrationOrganization(user: UserProfileRegister.Organization) : Boolean {
        return try {
            val response = api.registrationOrganization(userMapper.toDtoUserProfile(user))
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            userPreferences.saveUserId(response.user.id.toString())
            userPreferences.saveUserRole(response.user.role)
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun authorizationUser(login: String, password: String) : Boolean {
        return try {
            val response = api.authorization(LoginRequest(login, password))
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            userPreferences.saveUserId(response.user.id.toString())
            userPreferences.saveUserRole(response.user.role)
            true
        }
        catch (e: Exception){
            false
        }
    }

    override suspend fun getTasks(): List<Task> {
        return try {
            val response = api.getTasksVolunteer()
            response.map { it -> taskMapper.toDomain(it) }
        }
        catch (e: Exception) {
            emptyList<Task>()
        }
    }

    override suspend fun getTask(id: Int): Task? {
        return try {
            val response = api.getTask(id)
            taskMapper.toDomain(response)
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun getTasksOrganization(): List<Task> {
        return try {
            val response = api.getTasksOrganization()
            response.map { it -> taskMapper.toDomain(it) }
        }
        catch (e: Exception) {
            emptyList<Task>()
        }
    }

    override suspend fun updateTask(
        id: Int,
        title: String,
        description: String,
        address: String,
        category: String,
        volunteersNeeded: Int
    ): Int? {
        return try {
            val response = api.updateTask(
                TaskRequest(id, title, description, address, category, volunteersNeeded)
            )
            response
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun createTask(
        title: String,
        description: String,
        address: String,
        category: String,
        volunteersNeeded: Int
    ): Int? {
        return try {
            val response = api.createTask(
                CreateTaskRequest(title, description, address, category, volunteersNeeded)
            )
            response
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun getVolunteerInfo(): UserProfile.Volunteer? {
        return try {
            val response = api.getVolunteerInfo()
            userMapper.toDomainUserInfo(response)
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrganizationInfo(): UserProfile.Organization? {
        return try {
            val response = api.getOrganizationInfo()
            userMapper.toDomainUserInfo(response)
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun updateVolunteerInfo(userInfo: UpdateProfileParams.Volunteer): Boolean {
        return try {
            api.updateVolunteerInfo(userMapper.toDtoUpdateProfile(userInfo))
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun updateOrganizationInfo(userInfo: UpdateProfileParams.Organization): Boolean {
        return try {
            api.updateOrganizationInfo(userMapper.toDtoUpdateProfile(userInfo))
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun getFeedbacks(): List<Feedback> {
        return try {
            val response = api.getFeedbacks()
            response.map { feedbackMapper.toDomain(it) }
        }
        catch (e: Exception) {
            emptyList<Feedback>()
        }
    }

    override fun getCurrentUserRole(): UserRole? {
        return userPreferences.getUserRole()
    }

    override suspend fun deleteUser(): Boolean {
        return try {
            api.deleteUser()
            tokenManager.deleteToken()
            userPreferences.deleteUserIdAndRole()
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteTask(id: Int): Boolean {
        return try {
            api.deleteTask(id)
            true
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun logout() {
        tokenManager.deleteToken()
        userPreferences.deleteUserIdAndRole()
    }
}