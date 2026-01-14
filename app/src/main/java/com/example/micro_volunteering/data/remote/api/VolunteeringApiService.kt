package com.example.micro_volunteering.data.remote.api

import com.example.micro_volunteering.data.remote.dto.request.CreateTaskRequest
import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.OrganizationProfileRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterOrganizationRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterVolunteerRequest
import com.example.micro_volunteering.data.remote.dto.request.TaskRequest
import com.example.micro_volunteering.data.remote.dto.request.VolunteerProfileRequest
import com.example.micro_volunteering.data.remote.dto.response.AuthResponse
import com.example.micro_volunteering.data.remote.dto.response.OrganizationProfileResponse
import com.example.micro_volunteering.data.remote.dto.response.TaskResponse
import com.example.micro_volunteering.data.remote.dto.response.VolunteerProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VolunteeringApiService {

    @POST("/registration/volunteer")
    @Headers("NO_AUTH: true")
    suspend fun registrationVolunteer(@Body request: RegisterVolunteerRequest) : AuthResponse

    @POST("/registration/organization")
    @Headers("NO_AUTH: true")
    suspend fun registrationOrganization(@Body request: RegisterOrganizationRequest) : AuthResponse

    @POST("/authorization")
    @Headers("NO_AUTH: true")
    suspend fun authorization(@Body request: LoginRequest) : AuthResponse

    @GET("/tasks")
    suspend fun getTasksVolunteer() : List<TaskResponse>

    @GET("/organization/tasks")
    suspend fun getTasksOrganization() : List<TaskResponse>

    @GET("/task/{id}")
    suspend fun getTask(@Path("id") id: Int) : TaskResponse

    @PUT("/update-task")
    suspend fun updateTask(@Body request: TaskRequest) : Int

    @POST("/create-task")
    suspend fun createTask(@Body request: CreateTaskRequest) : Int

    @GET("/volunteer/profile")
    suspend fun getVolunteerInfo() : VolunteerProfileResponse

    @GET("/organization/profile")
    suspend fun getOrganizationInfo() : OrganizationProfileResponse

    @PUT("/volunteer/update-profile")
    suspend fun updateVolunteerInfo(@Body request: VolunteerProfileRequest) : VolunteerProfileResponse

    @PUT("/organization/update-profile")
    suspend fun updateOrganizationInfo(@Body request: OrganizationProfileRequest) : OrganizationProfileResponse

    @DELETE("/delete-account")
    suspend fun deleteUser() : Boolean

    @DELETE("/delete-task/{id}")
    suspend fun deleteTask(@Path("id") id: Int) : Boolean
}