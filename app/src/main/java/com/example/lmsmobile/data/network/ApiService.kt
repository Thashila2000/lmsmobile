package com.example.lmsmobile.data.network

import com.example.lmsmobile.data.model.LoginRequest
import com.example.lmsmobile.data.model.LoginResponse
import com.example.lmsmobile.data.model.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("tasks/schedule")
    suspend fun getScheduledTasks(@Query("degreeId") degreeId: Long): List<TaskDto>
}