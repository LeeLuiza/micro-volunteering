package com.example.micro_volunteering.data.repository

import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.local.UserPreferences
import com.example.micro_volunteering.data.mapper.TaskMapper
import com.example.micro_volunteering.data.mapper.UserMapper
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.TaskRequest
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.UpdateProfileParams
import com.example.micro_volunteering.domain.model.UserProfile
import com.example.micro_volunteering.domain.model.UserRole
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class VolunteeringRepositoryImpl @Inject constructor(
    private val api: VolunteeringApiService,
    private val tokenManager: TokenManager,
    private val userPreferences: UserPreferences,
    private val userMapper: UserMapper,
    private val taskMapper: TaskMapper
) : VolunteeringRepository {
    override suspend fun registrationVolunteer(user: UserProfile.Volunteer) : Boolean {
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

    override suspend fun registrationOrganization(user: UserProfile.Organization) : Boolean {
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
            val id = userPreferences.getUserId()?.toIntOrNull()

            if (id != null) {
                val response = api.getTasksOrganization(id)
                response.map { it -> taskMapper.toDomain(it) }
            }
            else {
                return emptyList<Task>()
            }
        }
        catch (e: Exception) {
            emptyList<Task>()
        }
    }

    override suspend fun updateTask(
        title: String,
        description: String,
        address: String,
        category: String,
        volunteersNeeded: Int
    ): Int? {
        return try {
            val idUser = userPreferences.getUserId()?.toIntOrNull()

            if (idUser != null) {
                val response = api.updateTask(
                    TaskRequest(idUser, title, description, address, category, volunteersNeeded)
                )
                response
            }
            else {
                return null
            }
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
            val idUser = userPreferences.getUserId()?.toIntOrNull()

            if (idUser != null) {
                val response = api.createTask(
                    TaskRequest(idUser, title, description, address, category, volunteersNeeded)
                )
                response
            }
            else {
                return null
            }
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun getVolunteerInfo(): UserProfile.Volunteer? {
        return try {
            val id = userPreferences.getUserId()?.toIntOrNull()

            if (id != null) {
                val response = api.getVolunteerInfo(id)
                userMapper.toDomainUserInfo(response)
            }
            else {
                return null
            }
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun getOrganizationInfo(): UserProfile.Organization? {
        return try {
            val id = userPreferences.getUserId()?.toIntOrNull()

            if (id != null) {
                val response = api.getOrganizationInfo(id)
                userMapper.toDomainUserInfo(response)
            }
            else {
                return null
            }
        }
        catch (e: Exception) {
            null
        }
    }

    override suspend fun updateVolunteerInfo(userInfo: UpdateProfileParams.Volunteer): Boolean {
        return try {
            val id = userPreferences.getUserId()?.toIntOrNull()

            if (id != null) {
                api.updateVolunteerInfo(userMapper.toDtoUpdateProfile(userInfo, id))
                true
            }
            else {
                false
            }
        }
        catch (e: Exception) {
            false
        }
    }

    override suspend fun updateOrganizationInfo(userInfo: UpdateProfileParams.Organization): Boolean {
        return try {
            val id = userPreferences.getUserId()?.toIntOrNull()

            if (id != null) {
                api.updateOrganizationInfo(userMapper.toDtoUpdateProfile(userInfo, id))
                true
            }
            else {
                false
            }
        }
        catch (e: Exception) {
            false
        }
    }

    override fun getCurrentUserRole(): UserRole? {
        return userPreferences.getUserRole()
    }
}