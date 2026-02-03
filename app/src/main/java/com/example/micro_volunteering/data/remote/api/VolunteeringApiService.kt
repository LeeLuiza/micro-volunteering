package com.example.micro_volunteering.data.remote.api

import com.example.micro_volunteering.data.remote.dto.request.CreateTaskRequest
import com.example.micro_volunteering.data.remote.dto.response.FeedbackResponse
import com.example.micro_volunteering.data.remote.dto.request.LoginRequest
import com.example.micro_volunteering.data.remote.dto.request.OrganizationProfileRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterOrganizationRequest
import com.example.micro_volunteering.data.remote.dto.request.RegisterVolunteerRequest
import com.example.micro_volunteering.data.remote.dto.request.TaskRequest
import com.example.micro_volunteering.data.remote.dto.request.TokenRequest
import com.example.micro_volunteering.data.remote.dto.request.VolunteerProfileRequest
import com.example.micro_volunteering.data.remote.dto.request.VolunteerRespondRequest
import com.example.micro_volunteering.data.remote.dto.response.AuthResponse
import com.example.micro_volunteering.data.remote.dto.response.NotificationResponse
import com.example.micro_volunteering.data.remote.dto.response.OrganizationProfileResponse
import com.example.micro_volunteering.data.remote.dto.response.TaskResponse
import com.example.micro_volunteering.data.remote.dto.response.VolunteerProfileResponse
import com.example.micro_volunteering.data.remote.dto.response.VolunteerRespondResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/feedbacks")
    suspend fun getFeedbacks() : List<FeedbackResponse>

    @DELETE("/delete-account")
    suspend fun deleteUser() : Boolean

    @DELETE("/delete-task/{id}")
    suspend fun deleteTask(@Path("id") id: Int) : Boolean

    @Multipart
    @POST("/upload/avatar")
    suspend fun uploadPhoto(@Part image: MultipartBody.Part): Boolean

    @GET("/notification")
    suspend fun getNotification() : List<NotificationResponse>

    @GET("/task/volunteer-respond/{id}")
    suspend fun getVolunteerRespond(@Path("id") idTask: Int) : List<VolunteerRespondResponse>

    @POST("/feedbacks/{id}")
    suspend fun leaveFeedback(@Path("id") idVolunteer: Int, @Body request: VolunteerRespondRequest) : Boolean

    @POST("/task/complete/{id}")
    suspend fun completeTask(@Path("id") id: Int) : Boolean

    @POST("task/{id}/respond")
    suspend fun respond(@Path("id") idTask: Int) : Boolean

    @POST("task/{id}/accept")
    suspend fun acceptVolunteer(@Path("id") idTask: Int, @Query("volunteerId") idVolunteer: Int) : Boolean

    @POST("task/{id}/dismiss")
    suspend fun dismissVolunteer(@Path("id") idTask: Int, @Query("volunteerId") idVolunteer: Int) : Boolean

    @POST("token")
    suspend fun updateFcmToken(@Body request: TokenRequest) : Boolean
}