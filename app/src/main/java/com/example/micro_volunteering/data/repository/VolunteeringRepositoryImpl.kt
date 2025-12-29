package com.example.micro_volunteering.data.repository

import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.local.UserPreferences
import com.example.micro_volunteering.data.mapper.TaskMapper
import com.example.micro_volunteering.data.mapper.UserMapper
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterRequest
import com.example.micro_volunteering.domain.model.Task
import com.example.micro_volunteering.domain.model.User
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import javax.inject.Inject

class VolunteeringRepositoryImpl @Inject constructor(
    private val api: VolunteeringApiService,
    private val tokenManager: TokenManager,
    private val userPreferences: UserPreferences,
    private val userMapper: UserMapper,
    private val taskMapper: TaskMapper
) : VolunteeringRepository {
    override fun registrationUser(
        fullName: String, phone: String, age: String, city: String, password: String
    ): User? {
        return try {
            val response = api.registration(RegisterRequest(fullName, phone, age, city, password))
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            userPreferences.saveUserId(response.user.id.toString())
            userMapper.toDomain(response.user)
        }
        catch (e: Exception) {
            null
        }
    }

    override fun authorizationUser(login: String, password: String): User? {
        return try {
            val response = api.authorization(LoginRequest(login, password))
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            userPreferences.saveUserId(response.user.id.toString())
            userMapper.toDomain(response.user)
        }
        catch (e: Exception){
            null
        }
    }

    override fun getTasks(): List<Task> {
        return try {
            val response = api.getTasks()
            response.map { it -> taskMapper.toDomain(it) }
        }
        catch (e: Exception) {
            emptyList<Task>()
        }
    }

    override fun getTask(id: Int): Task? {
        return try {
            val response = api.getTask(id)
            taskMapper.toDomain(response)
        }
        catch (e: Exception) {
            null
        }
    }

    override fun getTasksOrganization(): List<Task> {
        return try {
            val id = userPreferences.getUserId()?.toInt()

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

}