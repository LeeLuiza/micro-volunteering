package com.example.micro_volunteering.data.remote.api

import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterRequest
import com.example.micro_volunteering.data.remote.dto.response.AuthResponse
import com.example.micro_volunteering.data.remote.dto.response.TaskResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface VolunteeringApiService {

    @POST("/registration")
    @Headers("NO_AUTH: true")
    fun registration(@Body request: RegisterRequest) : AuthResponse

    @GET("/authorization")
    @Headers("NO_AUTH: true")
    fun authorization(@Body request: LoginRequest) : AuthResponse

    @GET("/tasks")
    fun getTasks() : List<TaskResponse>
}